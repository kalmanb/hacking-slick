package com.kalmanb
  
import com.kalmanb.test.TestSpec
import org.mockito.Matchers._
       
class FirstSlickTest extends TestSpec {

  import scala.slick.driver.JdbcDriver.backend.Database
  import Database.dynamicSession
  import scala.slick.jdbc.{GetResult, StaticQuery => Q}
  import Q.interpolation

  case class Person(
    firstName: String,
    lastName: String
    )


  describe("a first test") {
    it("should work") {
      Database.forURL("jdbc:mariadb://localhost/slickTest", driver = "org.mariadb.jdbc.Driver", user="root") withDynSession {
        Q.updateNA("""drop table if exists person""").execute 
        Q.updateNA("""create table person(
            id int not null auto_increment,
            firstname varchar(100) not null,
            lastName varchar(100) not null,
            PRIMARY KEY (id)
            )""").execute

        def insert(c: Person) = (Q.u + "insert into person (firstName, lastName) values (" +? c.firstName + "," +? c.lastName +")").execute
        
        // Insert some coffees
        Seq(
          Person("First1", "Last1"),
          Person("First2","Last2")
        ).foreach(insert)

        // Look at queries

        // Don't really like the implicit
        implicit val getPersonResult = GetResult(r => Person(r.<<, r.<<))

        def personByName(name:String)=sql"select * from person where firstName = $name".as[Person]
        println(personByName("First1").firstOption)
      }
    }
  }
}

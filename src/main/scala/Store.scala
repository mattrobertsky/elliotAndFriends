import scala.collection.mutable
import scala.io.Source
/**
  * Created by matt on 19/06/17.
  */
class Store {

  val dayReceiptMap: Map[java.util.Date, Reciept] = Map[java.util.Date, Reciept]().empty
  val stockMap: Map[String, String] = Map[String, String]().empty
  val itemsMap: Map[String, Item] = Map[String, Item]().empty
  var personMap: mutable.Map[String, Person] = mutable.Map[String, Person]().empty
  final val pathToPersons: String = new java.io.File(".").getCanonicalPath + "/src/main/resources/persons.txt"


  val readmeText : Iterator[String] = Source.fromResource("persons.txt").getLines

  def readPersons(): Unit = {
    println("in readPersons " + pathToPersons)
    for (line <- Source.fromFile(pathToPersons).getLines) {
      println(line)
      val args = line.split(",")
      println(args)
      if (args(0) == "customer") {
        createCustomer(args(1), args(2))
      } else {
        val isManager: Boolean = args(3) == "TRUE"
        createEmployee(args(1), args(2), isManager)
      }
    }
  }

  def createEmployee(someId: String, someName: String, isManager: Boolean): Employee =  {
    val employee = new Employee(someId, someName, isManager)
    personMap(employee.id) = employee
    employee
  }

  def createCustomer(someId: String, someName: String): Customer =  {
    val customer = new Customer(someId, someName)
    personMap(customer.id) = customer
    customer
  }


}
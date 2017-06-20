import scala.io.Source
/**
  * Created by matt on 19/06/17.
  */
class Store {

  val dayReceiptMap: Map[java.util.Date, Reciept] = Map[java.util.Date, Reciept]().empty
  val stockMap: Map[String, String] = Map[String, String]().empty
  val itemsMap: Map[String, Item] = Map[String, Item]().empty
  val personMap: Map[String, Person] = Map[String, Person]().empty
  final val pathToPersons: String = "../resources/persons.txt"





  def readPersons(): Unit = {
    println("in readPersons")
    val f = Source.fromFile(pathToPersons)
    for (line <- Source.fromFile(pathToPersons).getLines) {
      println(line)
//      val cw = line.split(" ")(0).toInt
//      val tmp = line.split(" ")(1).toInt
//      listOfPlaces += new PlaceAtTable(cw, tmp)
    }
  }

  def createEmployee(someId: String, someName: String, isManager: Boolean): Employee =  {
    new Employee(someId, someName, isManager)
  }

  def createCustomer(someId: String, someName: String): Customer =  {
    new Customer(someId, someName)
  }


}
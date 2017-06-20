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
//    file open pathToPersons
  }

}

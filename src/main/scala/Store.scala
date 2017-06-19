/**
  * Created by matt on 19/06/17.
  */
class Store {

  val dayReceiptMap: Map[java.util.Date, Reciept] = Map[java.util.Date, Reciept]().empty
  val stockMap: Map[Int, Int] = Map[Int, Int]().empty
  val itemsMap: Map[Int, Item] = Map[Int, Item]().empty
  val personMap: Map[Int, Person] = Map[Int, Person]().empty

}

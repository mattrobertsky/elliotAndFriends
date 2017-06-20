/**
  * Created by matt on 19/06/17.
  */
import scala.io.Source

class Store {

  var dayReceiptMap: Map[java.util.Date, Reciept] = Map[java.util.Date, Reciept]().empty
  var itemsMap: Map[String, Item] = Map[String, Item]().empty
  var personMap: Map[String, Person] = Map[String, Person]().empty
  final val pathToPersons: String = "../resources/persons.txt"
  final val pathToItems: String = "../resources/itemList.txt"



  def readPersons(): Unit = {

  }

  def readItems(): Unit ={
    for(line <- Source.fromFile(pathToItems).getLines){
      println(line)
    }
  }

  def addItem(item: Item): Unit ={
    //itemsMap += 'I' -> item
    //TO DO - ADD INDIVIDUAL ITEMS
  }

  def updateItem(name: String,update:Any):Unit= {
    val item = getItemByName(name)
    update match {
      case newName: String => item.name = newName
      case newQuantity: Int => item.quantity = newQuantity
      case newCost: Double => item.cost = newCost
      case newDate: java.util.Date => item.availableDate = newDate
      case _ => println("You can either update the Name:String,Quantity:Int,Cost:Double,releseDate:yyyy-mm-dd. Please Try Again using those format")
    }
  }

    def addStock(name: String, amount: Int): Unit = {
      getItemByName(name).quantity += amount
    }

    def removeStock(name: String, amount: Int): Unit = {
      getItemByName(name).quantity -= amount
    }

    def deleteItemByID(id: String): Unit ={
      itemsMap -= id
    }

    def deleteItemByName(name: String): Unit = {
      itemsMap.keys.foreach{items => if(itemsMap(items).name.equals(name)){deleteItemByID(items)}}
    }

    def getItemByID(id: String): Item ={
      itemsMap(id)
    }

    def getItemByName(name: String): Item = {
      var r = ""
      itemsMap.keys.foreach{items => if(itemsMap(items).name.equals(name)){ r = items }}
      getItemByID(r)
    }


}


/**
  * Created by matt on 19/06/17.
  */
import java.text.SimpleDateFormat

import scala.io.Source
import scala.collection.mutable

class Store {
  var dayReceiptMap: Map[java.util.Date, Reciept] = Map[java.util.Date, Reciept]().empty
  var stockMap: Map[String, String] = Map[String, String]().empty
  var itemsMap: mutable.Map[String, Item] = mutable.Map[String, Item]().empty
  var personMap: mutable.Map[String, Person] = mutable.Map[String, Person]().empty
  final val pathToPersons: String = new java.io.File(".").getCanonicalPath + java.io.File.separator + "src" +java.io.File.separator+"main"+java.io.File.separator+"resources"+java.io.File.separator+"persons.txt"
  final val pathToItems: String =  new java.io.File(".").getCanonicalPath + "/src/main/resources/itemList.txt"

  def tallyDayEarnings(date: java.util.Date): Double = {
    var total = 0.0
    dayReceiptMap.foreach(reciept => if(reciept._1.equals(date)){total += reciept._2.total})
    total
  }

  def readPersons(): Unit = {
    println("in readPersons " + pathToPersons)
    for (line <- Source.fromFile(pathToPersons).getLines) {
      println(line)
      val args = line.split(",")
      println(args)
      if (args(0) == "customer") {
        createCustomer(args(1))
      } else {
        val isManager: Boolean = args(2) == "TRUE"
        createEmployee(args(1), isManager)
      }
    }
  }


  def createEmployee(someName: String, isManager: Boolean): Employee =  {
    val employee = new Employee(someName, isManager)
    personMap(employee.id) = employee
    employee
  }

  def createCustomer(someName: String): Customer =  {
    val customer = new Customer(someName)
    personMap(customer.id) = customer
    customer
  }

  def createItem(availableDate:String, name:String, cost:Double, itemType:String, quantity:Int): Item ={
    val newItem = new Item(availableDate, name, cost, itemType, quantity)
    itemsMap(newItem.id) = newItem
    newItem
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

  def addItem(item: Item): Unit = {
    var location = item.id
  }

  def readItems(): Unit = {
    println("in readItems " + pathToItems)
    for (line <- Source.fromFile(pathToItems).getLines) {
      val args = line.split(",")
      createItem(args(0), args(1), args(2).toDouble, args(3), args(4).toInt)
      }
    }

  //MAKE THIS METHOD TAKE CUSTOMER ID?
  def sellItems(basket: List[Item]): Double = {
    var total = 0.0
    for (x <- 0 until basket.size) {
      if (basket(x).quantity > 0) { basket(x).quantity -= 1; total += basket(x).cost
      } else {println("Item " + basket(x).name + " is out of stock")}}
   total
  }

    def updateItemName(name: String,update:String):Unit= {
     getItemByName(name).name = update
    }

  def updateItemCost(name: String,update:Double):Unit= {
    getItemByName(name).cost = update
  }

  def updateItemQuantity(name: String,update:Int):Unit= {
    getItemByName(name).quantity = update
  }

  def updateItemDate(name: String,update:String):Unit= {
    getItemByName(name).availableDate = update
  }

  def addStock(name: String, amount: Int): Unit = {
    getItemByName(name).quantity += amount
  }

  def removeStock(name: String, amount: Int): Unit = {
    getItemByName(name).quantity -= amount
  }

  def main(args: Array[String]): Unit = {
    val store = new Store
    store.createItem("2018-1-1","Monster-Hunter",20.00,"Game",200)
    println(store.itemsMap)

  }
}
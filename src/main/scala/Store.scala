/**
  * Created by matt on 19/06/17.
  */
import scala.io.Source
import scala.collection.mutable

class Store {

  var dayReceiptMap: Map[java.util.Date, Reciept] = Map[java.util.Date, Reciept]().empty
  var stockMap: Map[String, String] = Map[String, String]().empty
  var itemsMap: Map[String, Item] = Map[String, Item]().empty
  var personMap: mutable.Map[String, Person] = mutable.Map[String, Person]().empty
  final val pathToPersons: String = new java.io.File(".").getCanonicalPath + "/src/main/resources/persons.txt"
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

  def readItems(): Unit ={
    for(line <- Source.fromFile(pathToItems).getLines){
      println(line)
    }
  }

  def addItem(item: Item): Unit ={
    //itemsMap += 'I' -> item
    //TO DO - ADD INDIVIDUAL ITEMS dsfsdsd
  }

  //MAKE THIS METHOD TAKE CUSTOMER ID AND HAVE A TOTAL COST?
  def sellItems(basket: List[Item]): Unit = {
    for (x <- 0 until basket.size) {
      if (basket(x).quantity > 0) {
        basket(x).quantity -= 1
      } else {
        println("Item " + basket(x).name + " is out of stock")
      }
    }
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
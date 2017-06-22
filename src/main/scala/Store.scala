/**
  * Created by matt on 19/06/17.
  */
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.regex.Pattern

import scala.io.Source
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class Store {

  var dayReceiptMap: Map[java.util.Date, ListBuffer[Reciept]] = Map[java.util.Date, ListBuffer[Reciept]]().empty
  var preOrderMap = Map[java.util.Date, ListBuffer[Reciept]]().empty
  var stockMap: Map[String, String] = Map[String, String]().empty
  var itemsMap: mutable.Map[String, Item] = mutable.Map[String, Item]().empty
  var personMap: mutable.Map[String, Person] = mutable.Map[String, Person]().empty
  var currentUser: Option[Employee] = None
  val calendar: java.util.Calendar = getCal
  final val pathToPersons: String = new java.io.File(".").getCanonicalPath + java.io.File.separator + "src" + java.io.File.separator + "main" + java.io.File.separator + "resources" + java.io.File.separator + "persons.txt"
  final val pathToItems: String =  new java.io.File(".").getCanonicalPath + java.io.File.separator + "src" + java.io.File.separator + "main" + java.io.File.separator + "resources" + java.io.File.separator + "itemList.txt"

  def tallyDayEarnings(date: java.util.Date): Double = {
    var total = 0.0
    try {
      val receipts: ListBuffer[Reciept] = dayReceiptMap(date)
      for (receipt <- receipts) {
        total += receipt.totalPrice
      }
    } catch {
      case e: NoSuchElementException => // do nowt
    }
    total
  }

  def testIsManager: Boolean = {
    if(currentUser.isDefined) {
      val empl:Employee = currentUser.get
      return empl.isManager
    }
    false
  }

  def tallyAllEarnings: Double = {
    var total = 0.0
    for (key <- dayReceiptMap.keys) {
      total += tallyDayEarnings(key)
    }
    total
  }

  def forecastDaysEarnings: Double = {
    tallyAllEarnings / dayReceiptMap.keys.size
  }

  def init: Unit = {
    this.readPersons()
    this.readItems()
  }

  def readPersons(): Unit = {
    for (line <- Source.fromFile(pathToPersons).getLines) {
      val args = line.split(",")
      if (args(0) == "customer") {
        createCustomer(args(1))
      } else {
        val isManager: Boolean = args(2) == "TRUE"
        createEmployee(args(1), isManager)
      }
    }
  }

  def getPerson(personId: String) ={
    personMap(personId)
  }

  def deletePerson(person: Person) = {
    personMap.remove(person.id)
  }

  def getPersonByID(id: String): Person ={
    personMap(id)
  }

  def getPersonByName(name: String): Person = {
    var r = ""
    personMap.keys.foreach{person => if(personMap(person).name.equals(name)){ r = person }}
    getPersonByID(r)
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

  def updateCustomerPoints(id: String, points: Int, increment: Boolean): Unit ={
    val customer: Customer = getPerson(id).asInstanceOf[Customer]
    if(increment) {
      customer.rewardPoints += points
    } else {
      customer.rewardPoints -= points
    }
  }

  def login(employee: Employee) = {
    currentUser = Option(employee)
  }

  def logout(employee: Employee) = {
    currentUser = None
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
    for (line <- Source.fromFile(pathToItems).getLines) {
      val args = line.split(",")
      createItem(args(0), args(1), args(2).toDouble, args(3), args(4).toInt)
      }
    }

  def addItemToBasket(customer: Customer, item: Item): Unit ={
    customer.basket += item
  }

  def processBasket(usePoints: Boolean, customer: Customer): Unit = {

    customer.basket.foreach(x => if(x.quantity == 0) {
      customer.basket -= x
      println("Item: "+x.name+ " is out of stock and has been removed from basket")
    })

    if(customer.basket.size <=0) {
      println("All items out of stock. Transaction cancelled.")
    } else {
      val total = calcTotal(customer.basket.toList)
      calcPoints(total.toInt, customer.id, usePoints)

      addReciept(customer.id, customer.basket.toList, total)
    }
    customer.emptyBasket
  }

  def calcTotal(basket: List[Item]): Double = {
    var total = 0.0
    for (x <- 0 until basket.size) {
      if (basket(x).quantity > 0) { basket(x).quantity -= 1; total += basket(x).cost
      } else {println("Item " + basket(x).name + " is out of stock")}}
    total
  }

  def calcPoints(total: Int, custID: String, usePoints: Boolean): Int = {
    var newTotal = total
    if (!usePoints) {
      val pointsTotal = newTotal / 10
      updateCustomerPoints(custID, pointsTotal, true)
    } else {
      val customer: Customer = getPerson(custID).asInstanceOf[Customer]
      if (customer.rewardPoints > newTotal) {
        updateCustomerPoints(custID, newTotal, false)
        newTotal = 0
      } else {
        newTotal -= customer.rewardPoints
        updateCustomerPoints(custID, customer.rewardPoints, false)
      }
    }
    newTotal
  }

  def updateItemName(name: String,update:String):Unit= {
   getItemByID(name).name = update
  }

  def updateItemCost(name: String,update:Double):Unit= {
    getItemByID(name).cost = update
  }

  def updateItemQuantity(name: String,update:Int):Unit= {
    getItemByID(name).quantity = update
  }

  def updateItemDate(name: String,update:String):Unit= {
    getItemByID(name).availableDate = update
  }

  def addStock(name: String, amount: Int): Unit = {
    getItemByID(name).quantity += amount
  }

  def removeStock(name: String, amount: Int): Unit = {
    getItemByID(name).quantity -= amount
  }

  private def getCal() = {
    val cal: Calendar = Calendar.getInstance()
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal
  }

  // this increments the local date
  def nextDay: Unit = {
    this.calendar.add(Calendar.DATE, 1)
  }

  // returns the local date (can be used as a key on the dayReceiptMap)
  def today: java.util.Date = {
    calendar.getTime
  }

  // this gives you the real time now (for adding to receipt lines when items are sold)
  def now: java.util.Date = {
    val cal = Calendar.getInstance()
    cal.set(Calendar.DATE, this.calendar.get(Calendar.DATE))
    cal.getTime
  }

  def addReciept(customerID:String, ItemList:List[Item], totalPrice:Double): Unit = {
    val reciept = new Reciept(customerID, ItemList, totalPrice)
    var receiptsList:ListBuffer[Reciept] = new ListBuffer[Reciept]()
    try {
      receiptsList = dayReceiptMap(this.today)
    } catch {
      case e: NoSuchElementException => {
        dayReceiptMap += (this.today -> receiptsList)
      }

    }
    receiptsList += reciept
    dayReceiptMap += (this.today -> receiptsList)
  }

  def printReciept(reciept:Reciept): String ={
    var str: String = ""
    val customer:Customer = getPerson(reciept.customerID).asInstanceOf[Customer]
    reciept.itemList.foreach(x => str +=  "- " + x.name + "  £" + f"${x.cost}%.2f" +
      {if(checkIfPreOrder(x.availableDate).after(today)){reciept.isPreOrder = true; " (Pre-order)"} else {""}}
      + "\n")
    "Customer: " + reciept.customerID + "\n\nItems: \n" + str + "\nTotal Price: £" + f"${reciept.totalPrice}%.2f" + "\n\nNew Points Total: " + customer.rewardPoints + "\n\n--- END OF RECIEPT ---\n\n"
  }

  def checkIfPreOrder(date : String): Date ={
    var Date = new SimpleDateFormat("dd/MM/yyyy").parse(date);
    Date
  }

  def listItems() =
  {
    println("Items: \n-----")
      itemsMap.foreach(x => println("Type: " + x._2.itemType + "  Product: " + x._2.name + "  Cost: £" + f"${x._2.cost}%.2f" + "  Qty: " + x._2.quantity + "\n"))
  }

  def listEmp() =
  {
    println("Employees: \n---------")
    personMap.foreach(x =>
    x._2.isInstanceOf[Employee] match
      {
        case false =>
        case true =>
        println("Employee ID: " + x._2.id)
      }
    )

  }

  def listCus() =
  {
    println("Customers: \n---------")
    personMap.foreach(x =>
    x._2.isInstanceOf[Customer] match
      {
      case false =>
      case true =>
        println("Customer ID: " + x._2.id)
      }
    )
  }

  def receiptList(date:String) =
  {
    var Date = today
    val rList = dayReceiptMap(Date)
    rList.foreach(x=>println(printReciept(x)))
  }

  def printPreOrders(): Unit ={
    sortPreOrderReciepts()
    preOrderMap.foreach(rec => rec._2.foreach(r => printReciept(r)))
  }
  def allReceipts()={

    dayReceiptMap.foreach(x => x._2.foreach(y => println("Date: " + x._1 + "\n" + printReciept(y))))
  }


  def sortPreOrderReciepts(): Unit ={
    var receiptsList:ListBuffer[Reciept] = new ListBuffer[Reciept]()
    dayReceiptMap.foreach(reciept => reciept._2.foreach(r => if(r.isPreOrder){
      receiptsList+= r
    }))
    try {
      receiptsList = preOrderMap(this.today)
    } catch {
      case e: NoSuchElementException => preOrderMap += (this.today -> receiptsList) // TODO refactor using Option
    }
  }

}

object Store {

  def main(args: Array[String]): Unit = {
    val store = new Store
    var messageElse = ""
    store.init
    doLogin

    def addElse = {
      messageElse = " else"
    }

    def doLogin: Unit = {
      val employeeId = readLine("Please login with your employee id\n")
      val employee = store.personMap(employeeId).asInstanceOf[Employee]
      store.login(employee)
      doPrompt
    }
    def doLogout: Unit   = {
      store.currentUser = None
      println("congratulations you are now logged out, you may now enjoy a hard earned lunch," +
              " providing Mat said it's ok...... \n \n \n which he probably didn't....\n\n\n " +
               "so get back to work fucker, no lunch for you.\n\n")

      doLogin
    }

    def doPrompt: Unit = {

      val taskId = readLine(s"\nwhat$messageElse would you like to do today ${store.currentUser.get.name}? \n\n" +
        s"[1] list employees      [4] list customers     [7] create item         [10] process basket  [13] tally day \n" +
        s"[2] create employee     [5] create customer    [8] set stock           [11] list receipts   [14] total tally\n" +
        s"[3] delete employee     [6] list items         [9] add item to basket  [12] list preorders  [15] forecast daily tally\n\n" +
        s"[16] close/open         [17]logout\n\n")

      taskId match {
        case "1" => addElse; doListEmployees
        case "2" => addElse; doCreateEmployee
        case "3" => addElse; doDeleteEmployee
        case "4" => addElse; doListCustomers
        case "5" => addElse; doCreateCustomer
        case "6" => addElse; doListItems
        case "7" => addElse; doCreateItem
        case "8" => addElse; doSetStock
        case "9" => addElse; doAddItemToBasket
        case "10" => addElse; doProcessBasket
        case "11" => addElse; doListReceipts
        case "12" => addElse; doListPreorders
        case "13" => addElse; doTallyDay
        case "14" => addElse; doTallyAllDays
        case "15" => addElse; doForecast
        case "16" => addElse; doNextDay
        case "17" => addElse; doLogout
        case _ => println("no such thing... shutting down your store due to user error")
      }
    }

    def doDeleteEmployee: Unit = {
        val empName = readLine("name:\n")
        val del = store.getPersonByName(empName)
        store.deletePerson(del)
    }


    def doListCustomers: Unit = {

    }
    def doCreateCustomer: Unit = {

    }
    def doListItems: Unit = {
        store.listItems()
        doPrompt
    }
    def doCreateItem: Unit = {
      if (store.testIsManager) {
        val date = readLine("release date: \n")
        val name = readLine("item name: \n")
        val cost = readLine("item cost: \n")
        val itemType = readLine("item type: \n")
        val quantity = readLine("item quantity: \n")

        store.createItem(date,name,cost.toDouble,itemType,quantity.toInt)
        doPrompt
      } else {
        println("You cannot create Items, please ask you manager")
        doPrompt
      }

    }
    def doSetStock: Unit = {
      if (store.testIsManager) {
        val ItemID = readLine("ItemID:\n")
        val ItemStock = readLine("How many Stock would you like to add:\n")
        store.addStock(ItemID,ItemStock.toInt)
        doPrompt
      } else {
        println("You cannot create employees, please ask you manager")
        doPrompt
      }
    }
    def doAddItemToBasket: Unit = {
      val customerName = readLine("customer name : \n")
      val itemName = readLine("item name: \n")
      val customer = store.getPersonByName(customerName).asInstanceOf[Customer]
      val item= store.getItemByName(itemName)
      store.addItemToBasket(customer, item)
      doPrompt

    }
    def doProcessBasket: Unit = {
        val isUsingPoints = readLine("Is Customer Using Points to Purchase?\n Y/N\n")
        var bool:Boolean = false
          isUsingPoints match {
            case y => bool = true
            case n=>bool = false
            case _=>println("Wrong Input: Please Try Again:\n");doProcessBasket
          }
        val buyingCustomer = readLine("Please Input Customer ID")
        store.processBasket(bool,store.getPersonByID(buyingCustomer).asInstanceOf[Customer])

    }
    def doListReceipts: Unit = {
      store.allReceipts()
      doPrompt
    }

    def doListPreorders: Unit = {
        store.printPreOrders()
        doPrompt
    }
    def doTallyDay: Unit = {
      println(s"days earnings £${store.tallyDayEarnings(store.today)}")
      doPrompt
    }
    def doTallyAllDays: Unit = {
      println(s"total earnings£${store.tallyAllEarnings}")
      doPrompt
    }
    def doForecast: Unit = {
      println(s"forcast earnings £${store.forecastDaysEarnings}")
      doPrompt
    }
    def doNextDay: Unit = {
      store.nextDay
      println(s"it's a brand new day, shame you're still ugly.. ${store.today}")
      doPrompt
    }

    def doListEmployees = {
      println()
      store.listEmp()
      doPrompt
    }
//

    def doCreateEmployee = {
      if (store.testIsManager) {
        val name = readLine("name:\n")
        val isManager = readLine("is manager: Y/N\n")
        store.createEmployee(name, isManager.equalsIgnoreCase("y"))
        doPrompt
      } else {
        println("ACCESS DENIED... jog on")
        doPrompt
      }
    }

  }
}
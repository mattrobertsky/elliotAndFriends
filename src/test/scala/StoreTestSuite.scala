import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StoreTestSuite extends FunSuite {
  val store: Store = new Store
  store.init

  test("customer:addToBasket: test if we can add an Item to the basket") {
    val item = store.getItemByName("Lara-Croft")
    val customer = store.createCustomer("Cary")
    assert(customer.basket.size == 0)
    customer.addToBasket(item)
    assert(customer.basket.size == 1)
    assert(customer.basket.contains(item))
    customer.emptyBasket
    assert(customer.basket.size == 0)
    assert(!customer.basket.contains(item))
  }

  test("store:tallyDayEarnings test the tally adds up correctly") {
    val item = store.getItemByName("Lara-Croft")
    val customer = store.createCustomer("Cary")
    customer.addToBasket(item)
    store.processBasket(false, customer)
    var tally = store.tallyDayEarnings(store.today)
    assert(tally == 40.00)
    customer.addToBasket(item)
    store.processBasket(false, customer)
    tally = store.tallyDayEarnings(store.today)
    assert(tally == 80.00)
  }

  test("store.readPersons: create some Persons from a file") {
    store.readPersons()
    assert(store.personMap.contains("CUS-1"))
  }

  test("store.createEmployee: create one manager using the constructor") {
    val manager: Employee = store.createEmployee("someName", true)
    assert(manager.name == "someName")
    assert(manager.isManager)
  }

  test("store.createEmployee: create one regular old employee using the constructor") {
    val employee: Employee = store.createEmployee("someName", false)
    assert(employee.name == "someName")
    assert(!employee.isManager)
  }

  test("store.customer: create one customer using the constructor") {
    val customer: Customer = store.createCustomer("someName")
    assert(customer.name == "someName")
    assert(customer.rewardPoints == 0)
  }

  test("store.deletePerson: create and delete a person") {
    val deleteMe = store.createCustomer("foo")
    store.deletePerson(deleteMe)
    assert(!store.personMap.contains(deleteMe.id))
  }

  test("store.getPerson: retrieve a person from the memory") {
    val getMe = store.createCustomer("Gary")
    val gotMe = store.getPerson(getMe.id)
    assert(getMe.id == gotMe.id)
    store.deletePerson(getMe)
  }

  test("store.login: employee can loginto system") {
    assert(!store.currentUser.isDefined)
    val larry = store.createEmployee("Larry", true)
    store.login(larry)
    assert(store.currentUser.isDefined)
    store.logout(larry)
    store.deletePerson(larry)
  }


  test("Store.readItems: create some Items from a file") {
    assert(store.itemsMap.nonEmpty)
  }


  test("Store.sellItem: Sell item from store") {
//    var customerBasket = List(store.getItemByName("Monster Hunter"),store.getItemByName("Lara-Croft"))
    val item = store.getItemByName("Monster Hunter")
    var originalM = item.quantity
    val item2 = store.getItemByName("Lara-Croft")
    var originalL = item2.quantity

//    store.sellItems(customerBasket,false, "CUS-1")
    val customer: Customer = store.getPerson("CUS-1").asInstanceOf[Customer]
    customer.addToBasket(item)
    customer.addToBasket(item2)

    store.processBasket(false, customer)
    assert(store.getItemByName("Monster Hunter").quantity == originalM-1)
    assert(store.getItemByName("Lara-Croft").quantity == originalL-1)
  }

    test("Store.deleteItems: deletes Items from a file") {
      store.deleteItemByID("ITM1")
      assert(!store.itemsMap.contains("ITM1"))
    }

    test("Store.addStock: adds items to the Map ") {
      val original = store.getItemByName("Monster Hunter").quantity
      store.addStock("Monster Hunter", 100)
      assert(store.getItemByName("Monster Hunter").quantity == original+100)
    }
    test("Store.removeStock: removes x quantity from item") {
      val original = store.getItemByName("Monster Hunter").quantity
      store.removeStock("Monster Hunter", 100)
      assert(store.getItemByName("Monster Hunter").quantity == original-100)
    }


  test("Store.calcTotal: calculates total from a list of items") {
    var customerBasket = List(store.getItemByName("Monster Hunter"), store.getItemByName("Lara-Croft"), store.getItemByName("Mario-Croft"))
    var compareTotal = store.getItemByName("Monster Hunter").cost + store.getItemByName("Lara-Croft").cost + store.getItemByName("Mario-Croft").cost
    assert(store.calcTotal(customerBasket) == compareTotal)
  }

  test("Store.sellItem: calculates total from a list of items") {
    var customerBasket = List(store.getItemByName("Monster Hunter"), store.getItemByName("Lara-Croft"), store.getItemByName("Mario-Croft"))
    var compareTotal = store.getItemByName("Monster Hunter").cost + store.getItemByName("Lara-Croft").cost + store.getItemByName("Mario-Croft").cost
    assert(store.calcTotal(customerBasket) == compareTotal)
  }

   //item, cost 0.0, isPreorder true/false
//  test("Store.sellItem: Sell  more items than are in stock") {
//    var customerBasket = List(store.getItemByName("Monster Hunter"))
//    store.getItemByName("Monster Hunter").quantity = 0
//    store.sellItems(customerBasket, false, "CUS-1")
//    assert(store.getItemByName("Monster Hunter").quantity == 0)
//  }


  test("Store.receiptList: should print out all the receipts in dailyreceiptList"){
    store.receiptList("21/06/2017")

  }


  test("Store.listItems: lists all items in the store"){
    println("--- Testing for item list ---")
    store.listItems()
    println("--- End of item test ---\n")
  }

  test("Store.listEmp: lists all employees in the store") {
    println("--- Testing for employee list ---")
    store.listEmp()
    println("--- End of employee test ---\n")
  }

  test("Store.listCus: lists all customers in the store"){
    println("--- Testing for customer list ---")
    store.listCus()
    println("--- End of customer test ---\n")
  }

  test("store.updateCustomerPoints: change reward points of the customer") {
    val original = store.createCustomer("Barry")
    val originalPoints = original.rewardPoints
    store.updateCustomerPoints(original.id, 10, true)
    assert(originalPoints != original.rewardPoints)
    store.deletePerson(original)
  }

  test("Store.calculatePoints1: calculates customer points if boolean is true") {
    val customer0: Customer = store.getPerson("CUS-8").asInstanceOf[Customer]
    val startingPoints = customer0.rewardPoints
    println(startingPoints)
    assert(store.calcPoints(30, "CUS-8", true) == (30-startingPoints))
  }

  test("Store.calculatePoints2: deducts customer points if customer has points"){
    val customer2: Customer = store.getPerson("CUS-8").asInstanceOf[Customer]
    val startingPoints = customer2.rewardPoints
    customer2.rewardPoints += 4
    assert(store.calcPoints(40, "CUS-8", true) == 36)
  }

  test("Store.calculatePoints3: deducts customer points if customer has points"){
    val customer3: Customer = store.getPerson("CUS-8").asInstanceOf[Customer]
    val startingPoints = customer3.rewardPoints
    customer3.rewardPoints += 50
    assert(store.calcPoints(500, "CUS-8", true) == 450)
  }

  test("Store.calculatePoints4: calculates customer points if boolean is false") {
    val customerD: Customer = store.getPerson("CUS-8").asInstanceOf[Customer]
    val startingPoints = customerD.rewardPoints
    val newPoints = store.calcPoints(20, "CUS-8", false)
    assert(customerD.rewardPoints == startingPoints + 2)
  }

  test("Store.updateItems: update Items from a file") {
    //println(store.itemsMap.size)
    store.updateItemCost("Monster Hunter", 50.00)
    store.updateItemQuantity("Monster Hunter", 200)
    store.updateItemDate("Monster Hunter", "11/06/2019")
    store.updateItemName("Monster Hunter", "Monster-Hunter-Remastered")

    assert(store.getItemByName("Monster-Hunter-Remastered").quantity == 200)
    assert(store.getItemByName("Monster-Hunter-Remastered").cost == 50.00)
    //assert(store.getItemByName("Monster-Hunter-Remastered").availableDate.after(2019-6-11))
    //FIX DATE
  }
}

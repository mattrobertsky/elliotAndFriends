import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StoreTestSuite extends FunSuite {


  val store: Store = new Store

  test("store.readPersons: create some Persons from a file") {
    store.readPersons()
    assert(store.personMap.size == 10)
    assert(store.personMap.contains("personID"))
  }

  test("store.createPerson: create one Customer using the constructor") {
    val customer: Customer = store.createPerson("someId", "someName", "customer")
    assert(customer.id == "someId")
    assert(customer.name == "someName")
    assert(customer.getClass.getName == "Customer")
  }

  test("store.createEmployee: create one Customer using the constructor") {
    val employee: Employee = store.createPerson("someId", "someName", "employee")
    assert(employee.id == "someId")
    assert(employee.name == "someName")
    assert(employee.getClass.getName == "Employee")
  }
  test("Store.readItems: create some Items from a file") {
    store.readItems()
    assert(store.itemsMap.size == 1)
    assert(store.itemsMap.contains("ItemID") == 1)

  }
  test("Store.sellItem: Sell item from store"){
    store.readItems()
    assert(store.itemsMap.contains("ItemID") == 2)
    asserts(store.itemsMap.key.quantity > 0)
    assert(var original = store.itemMap.key.quantity)
    assert(store.itemMap.key(2).removeStock(1))
    assert(store.itemMap.key.quantity == original-1)

  }

  test("Store.tallyDay: tally all transactions of the day")
  {
    val r1: RecieptItems = ("item1", 100.0, false)
    val r2: RecieptItems = ("item2", 150.0, false)
    val r3: ReceiptItems = ("item3", 250.0, true)

    val reciept: Reciept = ("reciept1", "customer1", 0.0)
    val reciept2: Reciept = ("reciept2", "customer2", 0.0)

    assert(reciept.itemsList += r1)
    assert(reciept.total += r1.cost)
    assert(reciept.itemsList += r2)
    assert(reciept.total += r3.cost)
    assert(reciept.itemsList += r3)
    assert(reciept.total += r3.cost)

    assert(Store.tallyDay == 1000.0)



      // item, cost 0.0, isPreorder true/false

  }
  // some comment


}

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StoreTestSuite extends FunSuite {


  val store: Store = new Store
  store.readItems()

  test("store.readPersons: create some Persons from a file") {
    store.readPersons()
    assert(store.personMap.size == 3)
    assert(store.personMap.contains("CUS-1"))
  }

  test("store.createEmployee: create one manager using the constructor") {
    val manager: Employee = store.createEmployee("someId", "someName", true)
    assert(manager.id == "someId")
    assert(manager.name == "someName")
    assert(manager.isManager)
  }
  test("store.createEmployee: create one regular old employee using the constructor") {
    val employee: Employee = store.createEmployee("someId", "someName", false)
    assert(employee.id == "someId")
    assert(employee.name == "someName")
    assert(!employee.isManager)
  }
  test("store.customer: create one customer using the constructor") {
    val customer: Customer = store.createCustomer("someId", "someName")
    assert(customer.id == "someId")
    assert(customer.name == "someName")
    assert(customer.rewardPoints == 0)
  }
  test("Store.readItems: create some Items from a file") {
    assert(store.itemsMap.nonEmpty)
  }

  test("Store.updateItems: update Items from a file") {
    store.updateItem("Monster Hunter", 50.00)
    store.updateItem("Monster Hunter", 200)
    store.updateItem("Monster Hunter", 2019-6-11)
    store.updateItem("Monster Hunter", "Monster-Hunter-Remastered")

    assert(store.getItemByName("Monster-Hunter-Remastered").quantity == 200)
    // assert(store.getItemByName("Monster-Hunter-Remastered").availableDate.after(2019-6-11))
    //FIX DATE
    assert(store.getItemByName("Monster-Hunter-Remastered").cost == 50.00)
  }

  test("Store.sellItem: Sell item from store") {
    var customerBasket = List(store.getItemByName("Monster Hunter"),store.getItemByName("Lara-Croft"))
    var originalM = store.getItemByName("Monster Hunter").quantity
    var originalL = store.getItemByName("Lara-Croft").quantity
    store.sellItems(customerBasket)
    assert(store.getItemByName("Monster Hunter").quantity == originalM-1)
    assert(store.getItemByName("Lara-Croft").quantity == originalL-1)
  }

  test("Store.sellItem: Sell  more items than are in stock") {
    var customerBasket = List(store.getItemByName("Monster Hunter"))
    store.getItemByName("Monster Hunter").quantity = 0
    store.sellItems(customerBasket)
    assert(store.getItemByName("Monster Hunter").quantity > 0)
  }

  test("Store.deleteItems: deletes Items from a file") {
    store.deleteItemByID("itemID")
    assert(store.itemsMap.contains("itemID"))
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
  // some comment


}

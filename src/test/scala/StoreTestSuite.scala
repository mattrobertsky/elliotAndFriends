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
    assert(store.itemsMap.size == 10)
    assert(store.itemsMap.contains("ItemID") == 1)

  }

  test("Store.updateItems: update Items from a file") {

    assert(store.itemsMap.updateItem(itemID,10) )
    assert(store.itemsMap.key(ItemID) == 10)

  }

  test("Store.deleteItems: deletes Items from a file") {

    assert(store.itemsMap.deleteItem(itemID) )
    assert(store.itemsMap.contains(ItemID))

  }
  test("Store.addStock: adds items to the Map ") {

    assert(store.itemsMap.addItem(item,quantity) )
    assert(store.itemsMap.contains(item))

  }
  test("Store.removeStock: removes x quantity from item") {

    assert(store.itemsMap.removeStock(Item,10) )
    assert(store.itemsMap.key(value.quantity) == 0)

  }


  // some comment


}

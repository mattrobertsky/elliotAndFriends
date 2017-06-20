import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StoreTestSuite extends FunSuite {


  val store: Store = new Store

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

}

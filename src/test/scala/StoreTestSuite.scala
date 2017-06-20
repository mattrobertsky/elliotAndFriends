import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StoreTestSuite extends FunSuite {
  val store = new Store()
  store.readItems()

//  test("store.readPersons: create some Persons from a file") {
//    store.readPersons()
//    assert(store.personMap.size == 10)
//    assert(store.personMap.contains("personID"))
//  }
//
//  test("store.createPerson: create one Customer using the constructor") {
//    val customer: Customer = store.createPerson("someId", "someName", "customer")
//    assert(customer.id == "someId")
//    assert(customer.name == "someName")
//    assert(customer.getClass.getName == "Customer")
//  }
//
//  test("store.createEmployee: create one Customer using the constructor") {
//    val employee: Employee = store.createPerson("someId", "someName", "employee")
//    assert(employee.id == "someId")
//    assert(employee.name == "someName")
//    assert(employee.getClass.getName == "Employee")
//  }
//  test("Store.readItems: create some Items from a file") {
//    assert(store.itemsMap.nonEmpty)
//  }
//
//  test("Store.updateItems: update Items from a file") {
//    store.updateItem("Monster Hunter", 50.00)
//    store.updateItem("Monster Hunter", 200)
//    store.updateItem("Monster Hunter", 2019-6-11)
//    store.updateItem("Monster Hunter", "Monster-Hunter-Remastered")
//
//    assert(store.getItemByName("Monster-Hunter-Remastered").quantity == 200)
//    // assert(store.getItemByName("Monster-Hunter-Remastered").availableDate.after(2019-6-11))
//    //FIX DATE
//    assert(store.getItemByName("Monster-Hunter-Remastered").cost == 50.00)
//  }
//  test("Store.sellItem: Sell item from store"){
//
//    assert(store.itemsMap.contains("ItemID") == 2)
//    assert(store.itemsMap.key.quantity > 0)
//    var original = store.itemMap.key.quantity
//    assert()
//    assert(store.itemMap.key(2).removeStock(1))
//    assert(store.itemMap.key.quantity == original-1)

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


//  test("Store.tallyDay: tally all transactions of the day")
//  {
//    val r1: RecieptItems = ("item1", 100.0, false)
//    val r2: RecieptItems = ("item2", 150.0, false)
//    val r3: ReceiptItems = ("item3", 250.0, true)
//
//    val reciept: Reciept = ("reciept1", "customer1", 0.0)
//    val reciept2: Reciept = ("reciept2", "customer2", 0.0)
//
//    assert(reciept.itemsList += r1)
//    assert(reciept.total += r1.cost)
//    assert(reciept.itemsList += r2)
//    assert(reciept.total += r3.cost)
//    assert(reciept.itemsList += r3)
//    assert(reciept.total += r3.cost)
//
//    assert(Store.tallyDay == 1000.0)



    // item, cost 0.0, isPreorder true/false

 // }
  // some comment


}

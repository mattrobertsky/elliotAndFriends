import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StoreTestSuite extends FunSuite {
  // put some tests in here

  val store: Store = new Store

  test("Store.readPersons: create some Persons from a file") {
    store.readPersons()
    assert(store.personMap.size == 10)
    assert(store.personMap.contains("personID"))
  }

//  import Main.countChange
//  test("countChange: example given in instructions") {
//    assert(countChange(4,List(1,2)) === 3)
//  }
//
//  test("countChange: sorted CHF") {
//    assert(countChange(300,List(5,10,20,50,100,200,500)) === 1022)
//  }
//
//  test("countChange: no pennies") {
//    assert(countChange(301,List(5,10,20,50,100,200,500)) === 0)
//  }
//
//  test("countChange: unsorted CHF") {
//    assert(countChange(300,List(500,5,50,100,20,200,10)) === 1022)
//  }


}

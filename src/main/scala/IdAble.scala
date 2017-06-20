/**
  * Created by Administrator on 20/06/2017.
  */
trait IdAble {

  val id: String
  var seq: Int = 0

  def nextId(): String

//  var personID = ""
//  var itemId = ""
//
//object IdAble {
//
//  def newPersonID(): String = {
//    personID += "PSN" + 1
//    personID
//  }
//
//  def newItemID(): String = {
//    itemId += "ITM" + 1
//    itemId
//
//
//  }
//}

}

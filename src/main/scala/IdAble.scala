/**
  * Created by Administrator on 20/06/2017.
  */
trait IdAble {
  var personID = ""
  var itemId = ""

object IdAble{
  def newPersonID(): String = {
    personID += "PSN" + 1
    personID
  }

  def newItemID(): String = {
    itemId += "ITM" + 1
    itemId


  }
}

}

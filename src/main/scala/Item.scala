/**
  * Created by Administrator on 19/06/2017.
  */
class Item (var availableDate:java.util.Date, var name:String, var cost:Double, final var itemType:String, var quantity:Int) extends IdAble{


  val id = nextId()

  override def nextId(): String = {
    seq += 1
    "ITM-" + seq
  }


}


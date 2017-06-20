/**
  * Created by Administrator on 19/06/2017.
  */
class Item (var availableDate: String, var name:String, var cost:Double, final var itemType:String, var quantity:Int) extends IdAble{


  val id = nextId()

  override def nextId(): String = {
    "ITM-" + IdAble.nextIndex()
  }


}


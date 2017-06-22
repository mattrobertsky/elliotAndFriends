class Reciept(val customerID:String, val itemList:List[Item],val totalPrice:Double, val thePoints:Int, val newPoints:Int)extends IdAble {
  val date:  String = ""
  var isPreOrder = false
  val id = this.nextId()

  override def nextId(): String = {
    "RCPT-" + IdAble.nextIndex()
  }

}

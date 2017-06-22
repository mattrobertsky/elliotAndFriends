class Reciept(val customerID:String, val itemList:List[Item],val totalPrice:Double)extends IdAble {
  val date:  java.util.Date = java.util.Calendar.getInstance().getTime
  var isPreOrder = false
  val id = this.nextId()

  override def nextId(): String = {
    "RCPT-" + IdAble.nextIndex()
  }

}

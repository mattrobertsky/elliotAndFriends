class Customer(override val name: String) extends Person(name) with IdAble {
  private val id = IdAble.newItemID()
  var rewardPoints: Int = 0
}

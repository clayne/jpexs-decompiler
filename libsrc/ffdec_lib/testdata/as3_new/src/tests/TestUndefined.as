package tests
{
	
	public class TestUndefined
	{
		public function run():*
		{
            var i:int;
            var j:int
            var c:int = 5 + i;
            var f = function() {
                trace(c);
                trace(j);
            };
            
            while (i < 10) {
                trace(i);
                i++;
            }
            
            f();
		}
	}
}

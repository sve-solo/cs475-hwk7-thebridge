/**
 * Runs all threads
 */

public class BridgeRunner {
	public static void main(String[] args) {

		//check command line inputs
		if(args.length < 2 || Integer.parseInt(args[0]) <= 0 || Integer.parseInt(args[1]) <= 0){
			//print error message
			System.out.println("Usage: javac BridgeRunner <bridge limit> <num cars>");
		}
		else{
			//get bridge capacity
			int bridgeLimit = Integer.parseInt(args[0]);

			//get number of threads
			int numThreads = Integer.parseInt(args[1]);
	
			//instantiate the bridge
			OneLaneBridge bridge = new OneLaneBridge(bridgeLimit);
			
			//allocate space for threads
			Thread[] carThreads = new Thread[numThreads];

			//start threads
			for (int i = 0; i < numThreads; i++) {
				carThreads[i] = new Thread(new Car(i, bridge));
				//start threads
				carThreads[i].start();

			}
			//join threads
			for (int i = 0; i < numThreads; i++) {
				try {
					carThreads[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			System.out.println("All cars have crossed!!");
	}

}
}
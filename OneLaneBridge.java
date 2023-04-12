public class OneLaneBridge extends Bridge{
        //shared vars
        //use size of ArrayList<> from Bridge class to check number of cars curr on bridge
        private Object enter = new Object(); //CV - one for entering
        private Object leave = new Object(); //CV - one for leaving
       
        //sets the bridge capacity
        private int bridgeLimit;

        public OneLaneBridge(int bridgeLimit){
            super();
            this.bridgeLimit = bridgeLimit;
        }

        @Override
        public void arrive(Car car) throws InterruptedException{
            synchronized (enter){ //lock on entering
                //need some stuff if you're first car on bridge
                if(bridge.isEmpty()){
                    //set bridge direction to the car's direction
                    direction = car.getDirection();

                    //set entry time
                    car.setEntryTime(currentTime);

                    //add car to bridge list
                    synchronized(this){
                        bridge.add(car); 
                    }
                    
                    //print the bridge
                    synchronized(this){
                        System.out.println("Bridge (dir=" + direction + "): " + bridge);
                    }

                    //increment size of bridge
                    currentTime++;

                }
                else{
                    while(direction != car.getDirection() || bridge.size() >= bridgeLimit){
                        try {
                            //unlock + wait on enter CV 
                            //System.out.println("\t" + car.toString() + "has called enter.wait()");
                            enter.wait();
                            //if there are no other cars on the bridge, change directions
                            if(bridge.isEmpty()){
                                direction = car.getDirection();
                            }
                        }
                        catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    } 

                    //set entry time
                    car.setEntryTime(currentTime);
                    
                    //add car to bridge list
                    synchronized(this){
                        bridge.add(car); 
                    }

                    //print the bridge
                    synchronized(this){
                        if(bridge.isEmpty()){
                            System.out.println("Bridge (dir=" + direction + "): []");
                        }
                        else{
                            System.out.println("Bridge (dir=" + direction + "):" + bridge);
                        }
                    }
                    
                    //increment size of bridge
                    currentTime++;
                }
            } 
        }
            
        //exit
        @Override
        public void exit(Car car) throws InterruptedException{
            //synchronized keyword on CVs
            synchronized (leave){ //lock on leaving
                while(bridge.isEmpty() || !car.equals(bridge.get(0))){
                    //System.out.println("\t" + car.toString() + " called leave.wait()");
                    leave.wait();
                    //System.out.println("\t" + car.toString() + " picking up from here?");
                }
                if(car.equals(bridge.get(0))){
                    //System.out.println("\t" + car.toString() + " exited");

                    //remove car from list
                    synchronized(this){
                        bridge.remove(car);
                    }

                    synchronized(this){
                    //print the bridge
                        if(bridge.isEmpty()){
                            System.out.println("Bridge (dir=" + direction + "): []");;
                        }
                        else{
                            System.out.println("Bridge (dir=" + direction + "): [" + bridge + "]");
                        }
                    }

                //notify cars that a car has left
                leave.notifyAll();

                synchronized(enter){
                    //System.out.println(car.toString() + " synch'd on enter");
                    if(bridge.size() < bridgeLimit){
                        //notify cars that they can enter
                        enter.notifyAll();
                    }
                }
            }
            }
        }
}

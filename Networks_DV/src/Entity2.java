

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Entity2 extends Entity
{    
	int minArray[] = new int[4];
	int[] neighbor = {1,1,0,1};
	// Perform any necessary initialization in the constructor
	public Entity2() {
		System.out.println("Entity 2 constructor is called");
		int[] vector = {3,1,0,2};
		
		for (int i = 0; i < 4; i++) {
			Arrays.fill(distanceTable[i], 999);
		}
		distanceTable[0][0] = 3;
		distanceTable[1][1] = 1;
		distanceTable[2][2] = 0;
		distanceTable[3][3] = 2;

		for(int i =0; i < distanceTable.length; i++){
	        if(i != 2){
	          Packet p = new Packet(2, i, vector);
	          NetworkSimulator.toLayer2(p);
	        }
	      }
		printDT();
	}

	// Handle updates when a packet is received. Students will need to call
	// NetworkSimulator.toLayer2() with new packets based upon what they
	// send to update. Be careful to construct the source and destination of
	// the packet correctly. Read the warning in NetworkSimulator.java for more
	// details.
	public void update(Packet p) {
		System.out.println("Update 2 method is called");
		System.out.println("Source:" + p.getSource() +"\n"+ "Destination:" + p.getDest());
		int tempCost, vectorChange = 0;
		int rr[] = new int[4];
		int src = p.getSource();
		for(int i = 0; i < 4; i++){
			if(i != src){ 
				tempCost = distanceTable[p.getSource()][p.getSource()] +p.getMincost(i); 
				if((tempCost != distanceTable[i][p.getSource()]) && (p.getMincost(i) < 999)){
	 				distanceTable[i][p.getSource()] = tempCost;
	 				vectorChange = 1;
	 			} 
	 		} 
	 	}
		if(vectorChange == 1){
			System.out.println(" Distance Table Updated in Entity 2");
			for(int k =0;k<neighbor.length;k++)
			{
	 			if(neighbor[k] == 1) {
		 			for(int i= 0; i < 4; i++) 
		 			{
		 				rr[i] = 999;
			 			for(int j=0;j<4;j++){
			 				if(j!=k) { 
			 					rr[i] = Math.min(rr[i],distanceTable[i][j]);
			 				}
			 			}
		 			}
		 			Packet temp = new Packet(2,k,rr);
		 			NetworkSimulator.toLayer2(temp);
		 			String value ="";
		 			for(int i=0;i<rr.length;i++){
		 				value += rr[i] + ",";
		 			}
		 			System.out.println("source:" + 2 + "destination:" + k + "contents" + value);
		 		}
	 		}
			vectorChange = 0;
		 	printDT();
		}
		
	 	
    }

    public void linkCostChangeHandler(int whichLink, int newCost){
    }

    public void printDT(){
        System.out.println();
        System.out.println("           via");
        System.out.println(" D2 |   0   1   3");
        System.out.println("----+------------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++){
            if (i == 2){
                continue;
            }

            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++){
                if (j == 2){
                    continue;
                }

                if (distanceTable[i][j] < 10){
                    System.out.print("   ");
                } else if (distanceTable[i][j] < 100) {
                    System.out.print("  ");
                } else {
                    System.out.print(" ");
                }

                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }
}
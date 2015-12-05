

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Entity3 extends Entity
{    
	int minArray[] = new int[4];
	int[] neighbor = {1,0,1,0};
	// Perform any necessary initialization in the constructor
	public Entity3() {
		System.out.println("Entity 3 constructor is called");
		int[] vector = {7,999,2,0};
		
		for (int i = 0; i < 4; i++) {
			Arrays.fill(distanceTable[i], 999);
		}
		distanceTable[0][0] = 7;
		distanceTable[1][1] = 999;
		distanceTable[2][2] = 2;
		distanceTable[3][3] = 0;

		for(int i =0; i < distanceTable.length; i++){
	        if(i != 1 && i!=3){
	          Packet p = new Packet(3, i, vector);
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
		System.out.println("Update 3 method is called");
		System.out.println("Source:" + p.getSource() +"\n"+ "Destination:" + p.getDest());
		int tempCost, vectorChange = 0;
		int rr[] = new int[4];
		int src = p.getSource();
		for(int i = 0; i < 4; i++){
			if(i!= src){ 
				tempCost = distanceTable[p.getSource()][p.getSource()] + p.getMincost(i); 
				if((tempCost != distanceTable[i][p.getSource()]) && (p.getMincost(i) < 999)){
	 				distanceTable[i][p.getSource()] = tempCost;
	 				vectorChange = 1;
	 			} 
	 		} 
	 	}
		if(vectorChange == 1){
			System.out.println(" Distance Table Updated in Entity 3");
			for(int k =0;k<neighbor.length;k++){	
	 			if(neighbor[k] == 1) {
	 				for(int i = 0; i < 4; i++) 
	 				{
	 					rr[i] = 999;
			 			for(int j=0;j<4;j++){
			 				if(j!=k) { 
			 					rr[i] = Math.min(rr[i],distanceTable[i][j]);
			 				}
			 			}
		 			}
	 				Packet temp = new Packet(3,k,rr);
		 			NetworkSimulator.toLayer2(temp);
		 			String value ="";
		 			for(int i=0;i<rr.length;i++){
		 				value += rr[i] + ",";
		 			}
		 			System.out.println("source:" + 3 + "destination:" + k + "contents" + value);
		 			
		 		}
	 		}
			vectorChange = 0;
		 	printDT();
		}
    }

    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }

    public void printDT()
    {
        System.out.println("         via");
        System.out.println(" D3 |   0   2");
        System.out.println("----+--------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            if (i == 3)
            {
                continue;
            }

            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j += 2)
            {

                if (distanceTable[i][j] < 10)
                {
                    System.out.print("   ");
                }
                else if (distanceTable[i][j] < 100)
                {
                    System.out.print("  ");
                }
                else
                {
                    System.out.print(" ");
                }

                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }
}
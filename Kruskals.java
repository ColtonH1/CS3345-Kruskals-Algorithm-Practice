/*
Colton Henderson
CS3345.002
Project 5
*/

import java.util.*;
import java.io.*;

public class Kruskals 
{
    //Lists to hold the edges (vertex u, vertex v, and the distance between them)
    private List<Edge> edges;
    //list to hold the city names and will be used later on for their index postion in the list
    private List<String> vertCount;

    Kruskals() throws Exception
    {
        edges = new ArrayList<>( );
        vertCount = new ArrayList<>(); 

        //read in file
        File file = new File("assn9_data.csv"); 
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line; 
        while ((line = br.readLine()) != null) //while there is a next line
        {
            //string array to hold each piece of information separatley 
            String[] arrOfCities = line.split(",");
            String u = " ", v = " ";
            int weight = -1;
        
            u = arrOfCities[0]; //the first catagory in each line is always u and u doesn't change

            for(int i = 1; i < arrOfCities.length; ++i)
            {
                v = arrOfCities[i]; //starts with i = 1
                i++; //adds 1 to i to make it even and go to the next element
                weight = Integer.parseInt(arrOfCities[i]); //sets the weight
                //for loop will add one more to make it odd again

                Edge newEdge = new Edge(weight, u, v);
                edges.add(newEdge);
                
                //if the list of vertex names doesn't contain this name, then add to the list
                if(!vertCount.contains(v))
                {
                    vertCount.add(v);
                }
            }
        }
        br.close(); //always close opened files
    }

    public class Edge implements Comparable<Edge> 
    {
        int weight; //distance from u to v
        String u, v; //vertex u and vertex v

        public Edge(int weight, String u, String v)
        {
            this.weight = weight;
            this.u = u;
            this.v = v;
        }

        //how we compare the edges to each other using pq
        @Override
        public int compareTo(Edge v) 
        { 
            return this.weight-v.weight; 
        }
    }

    //based on the psuedo code from the class book
    static List<Edge> kruskal(List<Edge> edges, List<String> vertCount) {
        int totalDist = 0;
        DisjSets ds = new DisjSets(vertCount.size()); //make a ds with the size equal to the amount of cities
        PriorityQueue<Edge> pq = new PriorityQueue<>(edges); //use pq to order the edges based on the compareTo in class Edge
        List<Edge> mst = new ArrayList<>();

        while (mst.size() != vertCount.size() - 1) {

            Edge e = pq.remove(); // Edge e = (u, v)
            // vertCount has a copy of every city.
            // Find the index of each city given the name in its edge class
            int uset = ds.find(vertCount.indexOf(e.u));
            int vset = ds.find(vertCount.indexOf(e.v));

            if (uset != vset) {
                // Accept the edge
                mst.add(e);
                ds.union(uset, vset);
            }
        }

        for (int i = 0; i < mst.size(); i++) {
            Edge finalTree = mst.get(i);
            //System.out.println(finalTree.u + "\t\t" + finalTree.v + "\t\t" + finalTree.weight);
            //format output so each variable has 20 character spaces in between them (20 minus character count of the word)
            System.out.println(String.format("%-20s %-20s %d" , finalTree.u, finalTree.v, finalTree.weight));
            totalDist += finalTree.weight;
        }

        System.out.println("Total Distance: " + totalDist);

        return mst;
    }

    public static void main(String[] args) throws Exception 
    {
        //create a new object to run everything
        Kruskals k = new Kruskals();
        kruskal(k.edges, k.vertCount);
    }
}
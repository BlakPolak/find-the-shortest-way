import java.io.*;
import java.util.*;


public class ReadFile {
    private ClassLoader classLoader = getClass().getClassLoader();
    private File file = new File(classLoader.getResource("cities.txt").getFile());
    private String filePath = file.getAbsolutePath();
    private Map<String, Integer> citiesHashMap;
    private List<String> citiesConnectionList = new ArrayList<>();
    public List<String> citiesConnectionAndCostsRepresentedByInt;

    public ReadFile() throws IOException {
        this.citiesHashMap = new HashMap<>();
        this.citiesConnectionAndCostsRepresentedByInt = new ArrayList<>();
        citiesToHashMap();
        fileLinesToList();
        this.citiesConnectionAndCostsRepresentedByInt = replaceCitiesNameByNumbers();
    }

    private List<String> replaceCitiesNameByNumbers() {
        List<String> connections = new ArrayList<>();
        int fromCityIndex = 0;
        int toCityIndex = 1;
        int costIndex = 2;
        String toCity = null;
        String fromCity = null;

        for (int connection = 0; connection < this.citiesConnectionList.size()-1; connection ++) {
            String[] parts = citiesConnectionList.get(connection).split("\\s");

            if (this.citiesHashMap.containsKey(parts[fromCityIndex])) {
                fromCity = this.citiesHashMap.get(parts[fromCityIndex]).toString();
            }

            if (this.citiesHashMap.containsKey(parts[toCityIndex])) {
                toCity = this.citiesHashMap.get(parts[toCityIndex]).toString();
            }

            String cost = parts[costIndex];
            connections.add(fromCity + " " + toCity + " " + cost);
        }
        return connections;
    }

    private void fileLinesToList() throws FileNotFoundException {
        FileReader file = new FileReader(this.filePath);  //address of the file
        Scanner sc = new Scanner(file);

        while( sc.hasNextLine() ){
            this.citiesConnectionList.add(sc.nextLine());
        }

        sc.close();
    }

    private int countNumberOfLines() throws IOException {
        int numberOfLines = 0;
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(this.filePath));
        } catch (FileNotFoundException e) {
            System.out.println("File error. Probably no such file in folder");
            e.getMessage();
        }

        while (bufferedReader.readLine() != null){
            numberOfLines ++;
        }

        bufferedReader.close();
        return  numberOfLines;
    }

    private void citiesToHashMap() throws IOException  {
        int numberOfLines = this.countNumberOfLines() -1;
        int vertexNumberForCity = 0;
        int actualLineNumber = 0;
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(this.filePath));
        } catch (FileNotFoundException e) {
            System.out.println("File error. Probably no such file in folder");
            e.getMessage();
        }

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            int fromCity = 0 ;
            int toCity = 1 ;
            String parts[] = line.split("\\s");

            if (!citiesHashMap.containsKey(parts[fromCity])){
                this.citiesHashMap.put(parts[fromCity], vertexNumberForCity);
                vertexNumberForCity++;
            }

            if (!citiesHashMap.containsKey(parts[toCity])){
                this.citiesHashMap.put(parts[toCity], vertexNumberForCity);
                vertexNumberForCity++;
            }

            if (numberOfLines == actualLineNumber){
                int startCityIndex = 0;
                int destinationCityIndex = this.citiesHashMap.size() -1;
                int tempFromVertexNumber = this.citiesHashMap.get(parts[fromCity]);
                int tempToVertexNumber = this.citiesHashMap.get(parts[toCity]);

                for (String city : this.citiesHashMap.keySet()) {

                    if (this.citiesHashMap.get(city).equals(startCityIndex)) {
                        this.citiesHashMap.put(city, tempFromVertexNumber);
                    } else if (this.citiesHashMap.get(city).equals(destinationCityIndex)){
                        this.citiesHashMap.put(city, tempToVertexNumber);
                    }

                    this.citiesHashMap.put(parts[fromCity], startCityIndex);
                    this.citiesHashMap.put(parts[toCity], destinationCityIndex);
                }
            }
            actualLineNumber ++;
        }
        bufferedReader.close();
    }

    public List<String> getCitiesConnectionAndCostsRepresentedByInt() {
        return citiesConnectionAndCostsRepresentedByInt;
    }

    public Map<String, Integer> getCitiesHashMap() {
        return citiesHashMap;
    }
}

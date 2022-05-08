public class Station {
    private String name;
    private int distance;


    public Station(){

    }

    public Station(String name,int distance){
        this.setName(name);
        this.setDistance(distance);
    }

    public Station(String name,String distance){
        this.setName(name);
        this.setDistance(Integer.valueOf(distance));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name =new String(name);
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean greater(Station station){
        if(this.getDistance()>station.getDistance())return true;
        if(this.getDistance()==station.getDistance()&&this.getName().compareTo(station.getName())>0)return true;
        return false;
    }
}

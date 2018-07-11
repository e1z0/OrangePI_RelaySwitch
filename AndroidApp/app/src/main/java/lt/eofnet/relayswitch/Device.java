package lt.eofnet.relayswitch;

/*
 * 2018 (c) justinas@eofnet.lt, EofNET LAB10
 */

public class Device {


    private int id;
    private String name;
    private int state;


    public Device() {
    }

    public Device(int id, String name, int state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public int GetId() {
        return id;
    }

    public void SetId(int id) {
        this.id = id;
    }

    public String GetName() {
        return name;
    }

    public void SetName(String name) {
        this.name = name;
    }

    public int GetState() {
        return state;
    }

    public void SetState(int state) {
        this.state = state;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device uzsakymas = (Device) o;

        if (id != uzsakymas.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}

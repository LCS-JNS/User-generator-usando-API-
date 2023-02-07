package RandUserAPI.code;

public class Result {
    private String gender;
    private Name name;
    private Location location;
    private String email;
    private Login login;
    private Dob dob;
    private Registered registered;
    private String phone;
    private String cell;
    private Id id;
    private Picture picture;
    private String nat;

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public Name getName() {
        return name;
    }
    public void setName(Name name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Login getLogin() {
        return login;
    }
    public void setLogin(Login login) {
        this.login = login;
    }

    public Dob getDob() {
        return dob;
    }
    public void setDob(Dob dob) {
        this.dob = dob;
    }

    public Registered getRegistered() {
        return registered;
    }
    public void setRegistered(Registered registered) {
        this.registered = registered;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }
    public void setCell(String cell) {
        this.cell = cell;
    }

    public Id getId() {
        return id;
    }
    public void setId(Id id) {
        this.id = id;
    }

    public Picture getPicture() {
        return picture;
    }
    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getNat() {
        return nat;
    }
    public void setNat(String nat) {
        this.nat = nat;
    }

    @Override
    public String toString() {
        String str = "-Nome: " + name.getFirst() + " " + name.getLast()
        + "\n-Gênero: " + gender
        + "\n-Idade: " + dob.getAge()
        + "\n-Nacionalidade: " + nat
        + "\n-Código: " + location.getPostcode()
        + "\n-País: " + location.getCountry()
        + "\n-Estado: " + location.getState()
        + "\n-Cidade: " + location.getCity()
        + "\n-Rua: " + location.getStreet().getName()
        + "\n-Número: " + location.getStreet().getNumber();
        return str;
    }
}

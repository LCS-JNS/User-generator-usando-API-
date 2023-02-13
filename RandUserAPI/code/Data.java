package RandUserAPI.code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.*;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Data {
    private String json;
    private final String usersPath = "User-generator-usando-API-/RandUserAPI/resources/users.json";
    private HashMap<String, Result> usersList = new HashMap<>();

    public void generateJSON() {
        final URL url;
        try {
            url = new URL("https://randomuser.me/api/?nat=br,us,gb,es");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((connection.getInputStream()), StandardCharsets.UTF_8));
            String input;
            StringBuffer json = new StringBuffer();
            while ((input = br.readLine()) != null) {
                json.append(input);
            }
            br.close();
            connection.disconnect();
            this.json = json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveJson() {
        byte[] jsonData;
        try {
            jsonData = Files.readAllBytes(Paths.get(this.usersPath));
            String content = new String(jsonData);
            if (!content.contains(",")) {
                content = content.substring(0, content.length() - 1) + this.json + "]";
            } else {
                content = content.substring(0, content.length() - 1) + "," + this.json + "]";
            }

            FileWriter writer = new FileWriter((usersPath), StandardCharsets.ISO_8859_1);
            writer.write(content);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getUsersFromJSON() {
        BufferedReader bfr;
        try {
            bfr = new BufferedReader(new FileReader((usersPath), StandardCharsets.ISO_8859_1));
            String input;
            StringBuffer json = new StringBuffer();
            while ((input = bfr.readLine()) != null) {
                json.append(input);
            }
            String str = json.toString();
            str = str.substring(1, str.length() - 1);
            str = str.replaceAll("\\},\\s*\\{", "}CutHere{");
            String[] list = str.split("CutHere");
            Gson gson = new Gson();
            for (String content : list) {
                Results results = gson.fromJson(content, Results.class);
                Result user = results.getResults().get(0);
                if(!usersList.containsKey(user.getId().getValue())) {
                    this.usersList.put(user.getId().getValue(), user);
                }
                
            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Result getUser(int index) {
        ArrayList<Result> temporary = new ArrayList<>();
        temporary.addAll(usersList.values());
        String id = temporary.get(index).getId().getValue();
        Result user = usersList.get(id);
        return user;
    }

    public Image getImage(Result user) {
        BufferedImage bufferedImage = null;
        try {
            URL url = new URL(user.getPicture().getLarge());
            bufferedImage = ImageIO.read(url);
            Image image = bufferedImage;
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Result getUserByName(String name) {
        ArrayList<Result> temporary = new ArrayList<>();
        temporary.addAll(usersList.values());
        String id = null;
        for(Result user : temporary) {
            if(getUserName(user).equals(name)) {
                id = user.getId().getValue();
            }
        }
        return usersList.get(id);
    }

    public int getUsersListSize() {
        return usersList.size();
    }

    public String getUserName(Result user) {
		String first = user.getName().getFirst();
		String last = user.getName().getLast();
		return first + " " + last;
	}

    public void deleteUsers() {
        FileWriter writer;
        try {
            writer = new FileWriter((usersPath), StandardCharsets.ISO_8859_1);
            writer.write("[]");
            writer.close();
            usersList.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getInfoAdministrativa(Result user) {
        String str = "-Email: " + user.getEmail()
        + "\n-Nome de usuário: " + user.getLogin().getUsername()
        + "\n-Senha: " + user.getLogin().getPassword()
        + "\n-Nome da identificação: " + user.getId().getName() 
        + "\n-Número da identificação: " + user.getId().getValue();
        return str;
    }
}
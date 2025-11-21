package manager;

import model.Movie;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieManager implements Persistable {
    private List<Movie> movies = new ArrayList<>();
    private final String FILE_PATH = "data/movies.csv";

    public MovieManager() {
        new File("data").mkdirs();
        loadData();
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void addMovie(String title, double price) {
        String id = "M" + (movies.size() + 1);
        movies.add(new Movie(id, title, price));
        saveData();
    }

    public boolean editMovie(String id, String newTitle, double newPrice) {
        for (Movie m : movies) {
            if (m.getId().equalsIgnoreCase(id)) {
                m.setTitle(newTitle);
                m.setBasePrice(newPrice);
                saveData();
                return true;
            }
        }
        return false;
    }

    public boolean deleteMovie(String id) {
        boolean removed = movies.removeIf(m -> m.getId().equalsIgnoreCase(id));
        if (removed) saveData();
        return removed;
    }

    public List<Movie> searchMovie(String keyword) {
        return movies.stream()
                .filter(m -> m.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Movie m : movies) {
                bw.write(m.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadData() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                // Logic sửa lỗi: Nếu mảng bị tách ra nhiều hơn 3 phần (do tên phim có dấu phẩy)
                if (parts.length >= 3) {
                    String id = parts[0];

                    // Giá tiền luôn là phần tử cuối cùng
                    double price = Double.parseDouble(parts[parts.length - 1]);

                    // Tên phim là nối các phần ở giữa lại (từ index 1 đến length-2)
                    StringBuilder titleBuilder = new StringBuilder();
                    for (int i = 1; i < parts.length - 1; i++) {
                        titleBuilder.append(parts[i]);
                        if (i < parts.length - 2) titleBuilder.append(","); // Thêm lại dấu phẩy nếu có
                    }
                    String title = titleBuilder.toString().trim(); // Xóa khoảng trắng thừa nếu có

                    movies.add(new Movie(id, title, price));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
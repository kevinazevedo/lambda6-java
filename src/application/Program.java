package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Product;

public class Program {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		List<Product> list = new ArrayList<>();
		
		System.out.print("Enter full file path: ");
		String path = scan.nextLine();
			
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();

			while (line != null) {	
				String[] fields = line.split(",");
				String name = fields[0];
				Double price = Double.parseDouble(fields[1]);
				
				list.add(new Product(name, price));			
				line = br.readLine();
			}	
			
			double average = list.stream()
					.map(p -> p.getPrice())
					.reduce(0.0, (x,y) -> x + y) / list.size();
			
			System.out.println("\nAverage price: " + String.format("%.2f", average));
			
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
			
			List<String> names = list.stream()
					.filter(p -> p.getPrice() < average)
					.map(p -> p.getName())
					.sorted(comp.reversed())
					.collect(Collectors.toList());
			
			names.forEach(System.out::println);			
		}
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}	
		
		scan.close();
	}
}

package com.example.pedidos;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(PedidosApplication.class, args);
		Map<String, List<String>> listaPedidos = new HashMap<>();
		List<String> arraylist = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		int opcionElegida = 0;
		String codigo;
		float precio;
		String pedido;
	
		while (opcionElegida != 4) {
			System.out.println("Introduce el numero de la opción que quieras:");
			System.out.println("1.- Introducir pedido");
			System.out.println("2.- Mostrar todos los pedidos");
			System.out.println("3.- Eliminar pedido");
			System.out.println("4.- Salir");
			opcionElegida = sc.nextInt();
	
			switch (opcionElegida) {
				case 1:
					System.out.println("Introduce el producto a pedir:");
					pedido = sc.next();
					System.out.println("Introduce el precio del producto:");
					precio = sc.nextFloat();
					guardarPedido(pedido, precio, listaPedidos,arraylist);
					break;
				case 2:
					mostrarPedidos(listaPedidos);
					break;
				case 3:
					System.out.println("Introduce el códido del pedido que quieres eliminar:");
					codigo = sc.next();
					eliminaPedido(codigo, listaPedidos);
					break;
				case 4:
					break;   // Si la opcion es 5 no se hace nada 
				default:
					System.out.println("Tienes que introducir una opción valida");
			}
	
		}
	}

	public static void guardarPedido(String pedido, float precio, Map<String, List<String>> listaPedidos, List<String> arraylist){
			String codigo=String.valueOf((listaPedidos.size()+100));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy 'at' HH:mm:ss");
			String date = String.valueOf(LocalDateTime.now().format(formatter));
			arraylist.add(date);
			arraylist.add(pedido);
			arraylist.add(Float.toString(precio));
			listaPedidos.put(codigo, arraylist);
			System.out.println("Tu Pedido Se ha guardado correctamente.");
			System.out.println("El codigo de su Pedido es: "+codigo+". Debe guardarlo por si desea eliminar su Pedido en un futuro");               
	}

	public static void mostrarPedidos(Map<String, List<String>> listaPedidos) {
		String clave;
		Iterator<String> pedidos = listaPedidos.keySet().iterator();
		System.out.println("Hay los siguientes pedidos:");
		while(pedidos.hasNext()){
			clave = pedidos.next();
			System.out.println(clave + " - " + listaPedidos.get(clave));
			}    
	}

	public static void eliminaPedido(String codigo, Map<String, List<String>> listaPedidos) {
		if (listaPedidos.containsKey(codigo)) {
			List<String> arrayList = listaPedidos.get(codigo);
			System.out.println("La Fecha de compra de su Pedido es: "+arrayList.get(0));               
			LocalDateTime time = LocalDateTime.parse(arrayList.get(0), DateTimeFormatter.ofPattern("dd-MM-yyyy 'at' HH:mm:ss")) ;
			if (!LocalDateTime.now().isBefore(time.plusHours(12))) {
				Double recargo = Double.valueOf(arrayList.get(3))*0.1;
				System.out.println("El pedido fue cancelado correctamente, pero han pasado mas de 12 horas y se cobro un recargo del 10%: "+recargo);				
			  } else {
				listaPedidos.remove(codigo);
				System.out.println("El pedido fue cancelado correctamente y no se cobro ningun recargo adicional");
			  }
		} else {
			System.out.println("No hay ningun pedido con ese código.");  
		}       
	}
}

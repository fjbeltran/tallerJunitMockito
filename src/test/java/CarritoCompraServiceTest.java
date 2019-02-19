import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.everis.bootcamp.tallerjunit.Articulo;
import com.everis.bootcamp.tallerjunit.BaseDeDatosService;
import com.everis.bootcamp.tallerjunit.CarritoCompraService;
import org.mockito.Mockito;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarritoCompraServiceTest {
	

	static CarritoCompraService carritotest;
	static BaseDeDatosService mock;
	
	@BeforeClass
	public static void inicializar() {
		
		System.out.println("Inicializamos el servicio");
		carritotest = new CarritoCompraService();
		mock = Mockito.mock(BaseDeDatosService.class);
		carritotest.setBbddService(mock);
		
	}
	
	@Test
	public void addArticuloTest() {
	//System.out.println("1");	
	Articulo a=new Articulo();	
	CarritoCompraService carritotest=new CarritoCompraService();
	carritotest.addArticulo(a);
	assertFalse(carritotest.getArticulos().isEmpty());	
	}
	
	@Test
	public void limpiarCestaTest() {
		
		//System.out.println("3");
		carritotest.limpiarCesta();
		
		assertTrue(carritotest.getArticulos().isEmpty());
	}
	
	@Test
	public void totalPriceTest() {
		
		//System.out.println("4");
		Articulo a=new Articulo();
		a.setPrecio(20.0);
		Articulo b=new Articulo();
		b.setPrecio(10.0);
		
		carritotest.addArticulo(a);
		carritotest.addArticulo(b);
		
		double resultado=carritotest.totalPrice();
		
		//assert.assertEquals(30.0, resultado, 0.0);
		assertEquals(55.0, resultado, 0.0);
		
	}
	
	
	@Test
	public void calculadorDescuentoTest() {
		//System.out.println("2");
		Articulo a=new Articulo();
		a.setPrecio(20.0);
		carritotest.addArticulo(a);
		
		double resultado=carritotest.calculadorDescuento(a.getPrecio(), 50);
		
		assertEquals(10.0, resultado, 0.0);
	}
	
	/*@Before
	public void before(){
		
		System.out.println("Inicializamos el servicio");
		carritotest = new CarritoCompraService();
		mock = Mockito.mock(BaseDeDatosService.class);
		carritotest.setBbddService(mock);
		
	}*/
	
	
	@Test
	public void ejercicio1() {
		
		System.out.println("Definiendo comportamiento de findArticuloById(1)");
		Mockito.when(carritotest.getBbddService().findArticuloById(Mockito.eq(1))).thenReturn(new Articulo("Chandal", 18.0));
		Double result=carritotest.aplicarDescuento(1, 12.0);
		System.out.println("Precio: "+result);
		Mockito.verify(mock,Mockito.times(1)).findArticuloById(1);
	}
	
	
	
	@Test(expected = Exception.class)
	public void ejercicio2() {
		System.out.println("Definiendo comportamiento de findArticuloById(0)");
		Mockito.when(carritotest.getBbddService().findArticuloById(0)).thenThrow(new RuntimeException());
		Double result=carritotest.aplicarDescuento(0, 12.0);
		System.out.println("Precio: "+result);
	}
	
	
	@Test
	public void reto() {
		
		/*********************Hacemos la llamada a BBDD Service***********************/
		
		long id=10;
		System.out.println("Definiendo comportamiento de Inserción");
		
		Articulo a=new Articulo();
		a.setPrecio(25.0);
		a.setDescripcion("Camiseta Mockito");
		a.setId(50L);
	
		Mockito.when(mock.insertArticulo(a)).thenReturn(100);
		long resultado=carritotest.insertar(a);
		
		/*******************Comprobamos resultados de TEST****************************/
		
		assertEquals(resultado, 100);
		
		assertTrue(carritotest.getArticulos().contains(a)); 
			
		
		Mockito.verify(mock,Mockito.atLeastOnce()).insertArticulo(a);
	}

}

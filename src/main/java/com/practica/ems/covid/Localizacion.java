package com.practica.ems.covid;


import java.util.Iterator;
import java.util.LinkedList;

import com.practica.excecption.EmsDuplicateLocationException;
import com.practica.excecption.EmsLocalizationNotFoundException;
import com.practica.genericas.FechaHora;
import com.practica.genericas.PosicionPersona;

public class Localizacion {
	LinkedList<PosicionPersona> lista;

	public Localizacion() {
		super();
		this.lista = new LinkedList<PosicionPersona>();
	};
	
	public LinkedList<PosicionPersona> getLista() {
		return lista;
	}

	public void setLista(LinkedList<PosicionPersona> lista) {
		this.lista = lista;
	}

	public void addLocalizacion (PosicionPersona p) throws EmsDuplicateLocationException {
		try {
			findLocalizacion(p.getDocumento(), p.getFechaPosicion().getFecha().toString(),p.getFechaPosicion().getHora().toString() );
			throw new EmsDuplicateLocationException();
		}catch(EmsLocalizationNotFoundException e) {
			lista.add(p);
		}
	}
	
	public int findLocalizacion (String documento, String fecha, String hora) throws EmsLocalizationNotFoundException {
	    int cont = 0;
	    Iterator<PosicionPersona> it = lista.iterator();
	    while(it.hasNext()) {
	    	cont++;
	    	PosicionPersona pp = it.next();
	    	FechaHora fechaHora = this.parsearFecha(fecha, hora);
	    	if(pp.getDocumento().equals(documento) && 
	    	   pp.getFechaPosicion().equals(fechaHora)) {
	    		return cont;
	    	}
	    } 
	    throw new EmsLocalizationNotFoundException();
	}
	public void delLocalizacion(String documento, String fecha, String hora) throws EmsLocalizationNotFoundException {
	    int pos=-1;
	    /**
	     *  Busca la localización, sino existe lanza una excepción
	     */
	    try {
			pos = findLocalizacion(documento, fecha, hora);
		} catch (EmsLocalizationNotFoundException e) {
			throw new EmsLocalizationNotFoundException();
		}
	    this.lista.remove(pos);
	    
	}
	
	void printLocalizacion() {    
	    System.out.print(this.toString());
	}

	@Override
	public String toString() {
		StringBuilder cadena = new StringBuilder();;
		for(PosicionPersona pp : lista) {
			cadena.append(formatearFechaPosicion(pp));
	    }
		
		return cadena.toString();		
	}

	private String formatearFechaPosicion(PosicionPersona pp) {
		FechaHora f = pp.getFechaPosicion();
		return String.format("%s;%02d/%02d/%04d;%02d:%02d;%.4f;%.4f\n",
				pp.getDocumento(),
				f.getFecha().getDia(), f.getFecha().getMes(), f.getFecha().getAnio(),
				f.getHora().getHora(), f.getHora().getMinuto(),
				pp.getCoordenada().getLatitud(), pp.getCoordenada().getLongitud());
	}

	@SuppressWarnings("unused")
	private FechaHora parsearFecha (String fecha) {
		return parsearFecha(fecha, "00:00");
	}
	
	private  FechaHora parsearFecha (String fecha, String hora) {
		int dia, mes, anio;
		String[] valores = fecha.split("\\/");
		dia = Integer.parseInt(valores[0]);
		mes = Integer.parseInt(valores[1]);
		anio = Integer.parseInt(valores[2]);
		int minuto, segundo;
		valores = hora.split("\\:");
		minuto = Integer.parseInt(valores[0]);
		segundo = Integer.parseInt(valores[1]);
		FechaHora fechaHora = new FechaHora(dia, mes, anio, minuto, segundo);
		return fechaHora;
	}
	
}

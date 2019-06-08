package openjsip.proxy;
import java.io.Serializable;
import java.net.*;

public class SLA implements Serializable{

	private int average_DR;
	private int peak_DR;
	private int burst_DR;
	
	public SLA (int aDR, int pDR, int bDR)
	{
		this.average_DR=aDR;
		this.peak_DR=pDR;
		this.burst_DR = bDR;
	}

	public int getADR()
	{
		return this.average_DR;
	}

	public int getPDR()
	{
		return this.peak_DR;
	}

	public int getBDR()
	{
		return this.burst_DR;
	}
	
	public boolean compare(Tspec spec)
	{
		if (this.average_DR > spec.getADR())
			if (this.peak_DR > spec.getPDR())
				if (this.burst_DR > spec.getBDR())
					return true;
		return false;
	}
	
	public String toString()
	{
		return("##SLA - average DR : " + this.average_DR + " peak DR : " + this.peak_DR + " burst DR : " + this.burst_DR);
	}

}

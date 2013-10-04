package com.truenorth.scoreware.membership.readers.hmrrc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.truenorth.scoreware.common.utility.DateParser;
import com.truenorth.scoreware.membership.readers.MembershipReader;
import com.truenorth.scoreware.Racer;

/**
 * Extends MembershipReader to read in the HMRRC membership list in excel format
 * @author bnorthan
 *
 */
public class HmrrcExcelReader extends MembershipReader
{
	public HmrrcExcelReader(String sourceName)
	{
		super(sourceName);
	}
	
	public void read()
	{
		try
		{
			int p=0;
			
			FileInputStream file = new FileInputStream(sourceName);
        
			//Get the workbook instance for XLS file 
			HSSFWorkbook workbook = new HSSFWorkbook(file);
		    
			//Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheetAt(0);
		 
			//Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = sheet.iterator();
		
			Row row = rowIterator.next();
			
			int i=0;
			
			int firstNameIndex=-1;
			int lastNameIndex=-1;
			int birthDateIndex=-1;
			int cityIndex=-1;
			int genderIndex=-1;
			
			for(Cell cell:row)
			{
				String name = cell.getStringCellValue();
				
				if (name.equals("First name"))
				{
					firstNameIndex=i;
				}
				if (name.equals("Last name"))
				{
					lastNameIndex=i;
				}
				if (name.equals("Birthdate (e.g., 01 Jun 1954)"))
				{
					birthDateIndex=i;
				}
				if (name.equals("City"))
				{
					cityIndex=i;
				}
				if (name.equals("Gender"))
				{
					genderIndex=i;
				}
				
				i++;
			}
			
			//Get iterator to all cells of current row
			//Iterator<Cell> cellIterator = row.iterator();
		
			//for (i=0;i<100;i++)
			while (rowIterator.hasNext())
			{
				Racer racer=new Racer();
				
				row = rowIterator.next();
				 
				Cell cell = row.getCell(firstNameIndex);
				
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) 
				{
			          // Nothing in the cell in this row, skip it   
				} 
				else 
				{
					racer.setFirstName(cell.getStringCellValue());
				}
					
				cell = row.getCell(lastNameIndex);
				
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) 
				{
			          // Nothing in the cell in this row, skip it   
				} 
				else 
				{
					racer.setLastName(cell.getStringCellValue());
				}
				
				cell = row.getCell(birthDateIndex);
				
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) 
				{
			          // Nothing in the cell in this row, skip it   
				} 
				else 
				{
					String dateString=cell.getStringCellValue();
					
					Date date=DateParser.getDate(dateString);
					
					if (date==null) 
					{
						p++;
						
					//	new java.util.Scanner(System.in).nextLine();
					}
					else
					{
						int age=DateParser.howOldInYears(date, new Date());
						racer.setAge(age);
					}
					
				}
				
				cell = row.getCell(cityIndex);
				
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) 
				{
			          // Nothing in the cell in this row, skip it   
				} 
				else 
				{
					racer.setCity(cell.getStringCellValue());
				}
				
				cell = row.getCell(genderIndex);
				
				if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) 
				{
			          // Nothing in the cell in this row, skip it   
				} 
				else 
				{
					String sex=cell.getStringCellValue();
					sex=sex.toLowerCase();					
					
					if (sex.equals("male"))
					{
						racer.setSex(Racer.Sex.MALE);
					}
					else if (sex.equals("m"))
					{
						racer.setSex(Racer.Sex.MALE);
					}
					else if (sex.equals("female"))
					{
						racer.setSex(Racer.Sex.FEMALE);
					}
					else if (sex.equals("f"))
					{
						racer.setSex(Racer.Sex.FEMALE);
					}
					else
					{
						racer.setSex(Racer.Sex.FEMALE);
					}
				}
	
				members.add(racer);
			}
			
			/*for(Racer racer:members)
			{
				System.out.println(racer.getFirstName()+" "+racer.getLastName()+" "
						+racer.getCity()+" "+racer.getAge()+" "+racer.getSex());
				
			}
			System.out.println("problems detected: "+ p);*/
		}
		
		catch (FileNotFoundException e) 
		{
		    e.printStackTrace();
		} 
		catch (IOException e) 
		{
		    e.printStackTrace();
		}
	}

}

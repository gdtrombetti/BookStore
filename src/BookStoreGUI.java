/* Gino Trombetti
  Description: An application that works like a shopping cart
  system for a book store.
 */

//Import needed packages
import java.awt.*;
import java.awt.event.*;

import java.util.Scanner;
import java.io.*;

import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.*;

import java.text.DecimalFormat;

public class BookStoreGUI extends JFrame {

	private static final int WINDOW_WIDTH= 800;		//Width of GUI frame
	private static final int WINDOW_LENGTH = 250;	//Length of GUI frame

	private JPanel booksPanel;			//Holds all the books
	private JPanel buttonsPanel;		//Has add/remove/checkout buttons
	private JPanel shoppingCartPanel;	//To hold books added by user
	private JPanel bannerPanel;			//Banner panel
	private JPanel searchButtonsPanel;	//Holds search/showall buttons

	private JList booksList;			//List with all book names
	private JList selectedList;			//List in shopping cart

	private JButton addSelected;		//Adds book to shopping cart
	private JButton removeSelected;		//Removes book from shopping cart
	private JButton checkOut;			//Adds all books prices + taxes
	private JButton searchButton;		//Searches for desired book
	private JButton showAllButton;		//shows all books available


	private BookInfo booksInfo = new BookInfo(); 			//BookInfo object
	private String[] bookNames = booksInfo.getBookNames();	//Array that Holds all book names
	private double[] bookPrices = booksInfo.getBookPrices();//Array that holds all book prices


	private JScrollPane scrollPane1;	//Holds available books list
	private JScrollPane scrollPane2;	//Holds selected book list

	private JLabel panelTitle;			//Panel title
	private JLabel cartTitle;			//Panel title
	private JLabel banner;				//Panel title

	private JTextField searchField;		//Allows user to input search

	private int element = -1;			// control variable
	private int selectedIndex;			//Index selected among available books
	private int index;					//Int that holds selected index.
	private int i,count;				//Control variables

	private double total;				//Total of prices
	private double bookPrice;			//Hold book prices
	private final double TAX=0.06;		//Constant for tax value

	private ListModel books;			//List model for book name list
	private ListModel shoppingCart;		//List model for shopping cart list
	private DefaultListModel shoppingCartDFM;

	private DecimalFormat money;		//Money format
	private Object selectedBookName; 	//Selected book

	private String searchResults;		//Hold search results
	private String notFound = " Title not found";	//Holds search results

	private boolean found = false;		//Boolean holds false if search results not found

	/*Constructor
	 * BookStoreGUI - Buuilds a GUI with multiple panels
	 */
	public BookStoreGUI() throws IOException{
		//Title of GUI
		setTitle("Book Store Shopping Cart");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(WINDOW_WIDTH, WINDOW_LENGTH);

		//BuildPanels
		buildBooksPanel();
		buildButtonsPanel();
		buildShoppingCartPanel();
		buildBannerPanel();
		buildSearchButtonsPanel();

		//Add panels to GUI frame
		add(bannerPanel,BorderLayout.NORTH);
		add(booksPanel, BorderLayout.WEST);
		add(buttonsPanel, BorderLayout.CENTER);
		add(shoppingCartPanel, BorderLayout.EAST);
		add(searchButtonsPanel, BorderLayout.SOUTH);

		//set visibility
		setVisible(true);
		pack();
	 }

	 //METHODS
	 /*
	  *buildBooksPanel() - Builds panel containing a JList/ScrollPane
	  */
	 public void buildBooksPanel(){

		//Create panel to hold list of books
		booksPanel = new JPanel();

		//Set Panel layout
		booksPanel.setLayout(new BorderLayout());

		//Create the list
		booksList = new JList(bookNames);

		//Set selection preferrence
		booksList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		//Visible book names
		booksList.setVisibleRowCount(5);

		//Create scroll pane containing book list
		scrollPane1 = new JScrollPane(booksList);
		scrollPane1.setPreferredSize(new Dimension(175,50));

		//JLabel/Panel title
	 	panelTitle = new JLabel("Available Books");

		//Add JLabel and scroll to panel
		booksPanel.add(panelTitle, BorderLayout.NORTH);
		booksPanel.add(scrollPane1);
	 }

	/*
	 * buildButtonsPanel - builds panel containing add/remove/checkout buttons
	 */
	 public void buildButtonsPanel(){
		//Create panel to hold buttons
		buttonsPanel = new JPanel();
		//Set Layout
		buttonsPanel.setLayout(new GridLayout(3,1));
		//Create Buttons
		addSelected = new JButton("Add Selected Item");
		removeSelected = new JButton("Remove Selected Item");
		checkOut = new JButton("Check Out");

	   	//add Listeners
		addSelected.addActionListener(new AddButtonListener());
		removeSelected.addActionListener(new RemoveButtonListener());
		checkOut.addActionListener(new CheckOutButtonListener());

		//Add button panel to GUI
	 	buttonsPanel.add(addSelected);
		buttonsPanel.add(removeSelected);
		buttonsPanel.add(checkOut);
	}
	/*
	 * buildShoppingCartPanel builds panel containing JList/Scroll pane
	 */
	public void buildShoppingCartPanel(){
		//Create panel
		shoppingCartPanel = new JPanel();

		//Set panel layout
		shoppingCartPanel.setLayout(new BorderLayout());

		//Create shopping cart list
		selectedList = new JList();

		//Set row visility
		selectedList.setVisibleRowCount(5);

		//Create scrollpane containin selected list items
		scrollPane2 = new JScrollPane(selectedList);

		scrollPane2.setPreferredSize(new Dimension(175,50));
		//JLabel/Panel title
		cartTitle = new JLabel("Shopping Cart ");

		//Add JLabel and scroll pane to panel
		shoppingCartPanel.add(cartTitle, BorderLayout.NORTH);
		shoppingCartPanel.add(scrollPane2);
	}

	/*
	 * buildBannerPanel - builds panel containing banner for GUI
	 */
	public void buildBannerPanel(){
		//Create panel
		bannerPanel = new JPanel();

		//Set Border layout
		setLayout(new BorderLayout());

		//String containing JLabel text
		String labelText= "<html><b COLOR=RED> Welcome</b>" + "<b><i COLOR=#006363> To </i></b>" +
							"<b><u COLOR=#BF3030>As</u><u COLOR=#8170D8>The</u><u COLOR=#00CC00>Pages</u><u COLOR=BLUE>Turn.com</u></b>" ;

		//create JLabel
		JLabel banner = new JLabel(labelText);
		banner.setFont(new Font("Serif",Font.BOLD,28));

		//add banner to panel
		bannerPanel.add(banner);
	}

	/*
	 * buildSearchButtonsPanel - builds panel containing search and showall buttons
	 */
	public void buildSearchButtonsPanel(){
		//Create panel
		searchButtonsPanel = new JPanel();

		//Set Border layout
		searchButtonsPanel.setLayout(new GridLayout(1, 3 ,5,5));
		//Create buttons
		searchButton = new JButton("Search");
		showAllButton = new JButton("Show All");

		//Create text field
		searchField = new JTextField(15);

		//Add listeners
		searchButton.addActionListener(new SearchButtonListener());
		showAllButton.addActionListener(new ShowAllButtonListener());

		//Add buttons and text field to panel
		searchButtonsPanel.add(searchField);
		searchButtonsPanel.add(searchButton);
		searchButtonsPanel.add(showAllButton);
	}

	//ACTION LISTENERS
	/*
	 * AddButtonListener - adds selected item to shopping cart upon selection
	 */
	public class AddButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			selectedIndex = booksList.getSelectedIndex();
			selectedBookName = booksList.getSelectedValue();

			books = booksList.getModel();
			shoppingCart = selectedList.getModel();

			shoppingCartDFM = new DefaultListModel();

			for(count=0; count<shoppingCart.getSize(); count++){
				shoppingCartDFM.addElement(shoppingCart.getElementAt(count));
			}

			if(element == -1)
				bookPrice += bookPrices[selectedIndex];
			else
				bookPrice += bookPrices[element];

			shoppingCartDFM.addElement(selectedBookName);
			selectedList.setModel(shoppingCartDFM);
		}
	}
	/*
	 * RemoveButtonListener - Removes selected item from shopping cart upon selection
	 */
	public class RemoveButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			index = selectedList.getSelectedIndex();
			((DefaultListModel)selectedList.getModel()).remove(index);

			if(element == -1)
				if(bookPrices[selectedIndex] <= bookPrice)
					bookPrice -= (bookPrices[selectedIndex]);
				else
					bookPrice = (bookPrices[index]) - bookPrice;
			else
				if(bookPrices[element] <= bookPrice)
					bookPrice -= (bookPrices[element]);
				else
					bookPrice = (bookPrices[index]) - bookPrice;
		}
	}

	/*
	 * CheckOutButtonListener - Calculates total and displays it to user
	 */
	public class CheckOutButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			money = new DecimalFormat("#,##0.00");
			total = (bookPrice + (bookPrice*TAX));

			JOptionPane.showMessageDialog(null, "Subtotal: $" + (money.format(bookPrice)) + "\n" +
												"Tax: $" + (money.format((bookPrice*TAX))) + "\n" +
												"Total: $" + (money.format(total)));
		}
	}

	/*
	 * SearchButtonListener - searches for user desired item
	 */
	public class SearchButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			index = 0;

			while(!found && index < bookNames.length)
			{
				if(bookNames[index].equals(searchField.getText())){
					found = true;
					element = index;
				}
				index++;
			}

			if(element == -1){
				booksList.setModel(new DefaultListModel());
				((DefaultListModel)booksList.getModel()).addElement(notFound);
			}
			else{
				searchResults = bookNames[element];
				booksList.setModel(new DefaultListModel());

				((DefaultListModel)booksList.getModel()).addElement(searchResults);
			}
		}
	}

	/*
	 * ShowsAllButtonListener - shows all available books
	 */
	public class ShowAllButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			booksList.setModel(new DefaultListModel());
			
			for(i=0; i < bookNames.length; i++){
				((DefaultListModel)booksList.getModel()).addElement(bookNames[i]);
				 
			}
		}
	}

	 public static void main(String[] args) throws IOException{

		 new BookStoreGUI();
	 }
 }
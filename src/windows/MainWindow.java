package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.table.JTableHeader;

import student.Student;
import table.ColumnModel;
import table.ExamTableModel;
import table.GroupableTableHeader;
import XML.FileChooserListener;
import XML.FileSaver;
import constants.MenuName;
import constants.Path;

public class MainWindow {
	private JComboBox<Integer> numberExams;
	private JComboBox<Integer> numberRecords;
	private ExamTableModel tableModel;
	private JTable table;
	private List<Student> studentList;
	private JMenuItem addPeopleItem;
	private PageToggle pageToggle;
	private JMenuItem openFileItem;

	public MainWindow() {

		JFrame frame = new JFrame(MenuName.TITLE);
		frame.setSize(700, 400);
		frame.setLocationRelativeTo(null);
		// frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.setVisible(true);

		numberExams = new JComboBox<Integer>(MenuName.NUMBER);
		numberRecords = new JComboBox<Integer>(MenuName.MARK);
		numberExams.setSelectedItem(2);
		numberRecords.setSelectedItem(2);
		createMenu(frame);
		createTable((Integer) numberExams.getSelectedItem(), frame);
		createToolBar(frame);
	}

	void createMenu(JFrame frame) {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu(MenuName.FILE);
		JMenu tableMenu = new JMenu(MenuName.TABLE);

		JMenuItem searchPeopleItem = new JMenuItem(MenuName.SERCH_PEOPLE);
		addPeopleItem = new JMenuItem(MenuName.ADD_PEOPLE);
		JMenuItem deletePeopleItem = new JMenuItem(MenuName.DELETE_PEOPLE);

		//JMenuItem newFileItem = new JMenuItem(MenuName.NEW_FILE);
		openFileItem = new JMenuItem(MenuName.OPEN_FILE);
		JMenuItem saveFileItem = new JMenuItem(MenuName.SAVE_AS);
		JMenuItem exitItem = new JMenuItem(MenuName.EXIT);

		openFileItem.setIcon(new ImageIcon(Path.OPEN_ICON.getPath()));
		saveFileItem.setIcon(new ImageIcon(Path.SAVE_ICON.getPath()));
		searchPeopleItem.setIcon(new ImageIcon(Path.SEARCH_ICON.getPath()));
		addPeopleItem.setIcon(new ImageIcon(Path.ADD_ICON.getPath()));
		deletePeopleItem.setIcon(new ImageIcon(Path.DELETE_ICON.getPath()));

		menuBar.add(fileMenu);
		menuBar.add(tableMenu);

		//fileMenu.add(newFileItem);
		fileMenu.add(openFileItem);
		fileMenu.add(saveFileItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		tableMenu.add(addPeopleItem);
		tableMenu.add(deletePeopleItem);
		tableMenu.add(searchPeopleItem);

		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});

		searchPeopleItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SearchDialog(frame, table, studentList, pageToggle);
			}
		});

		deletePeopleItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SearchDialog(frame, table, studentList, pageToggle)
						.startRemove(true);
			}
		});
		
		

	}

	@SuppressWarnings("serial")
	void createTable(int numberExams, JFrame frame) {
		List<Integer> d = new ArrayList<Integer>();
		d.add(9);
		d.add(5);

		List<Integer> d2 = new ArrayList<Integer>();
		d2.add(8);
		d2.add(9);

		List<String> d1 = new ArrayList<String>();
		d1.add("�����");
		d1.add("�����");

		tableModel = new ExamTableModel(numberExams);
		studentList = tableModel.getStudentList();
		Student student = new Student("����", "���������", "��������", 321702,
				d1, d);
		Student student1 = new Student("�������", "�������", "����������",
				321701, d1, d2);
		Student student2 = new Student("2", "������", "", 321701, d1, d2);
		Student student3 = new Student("3", "������", "", 321702, d1, d2);
		Student student4 = new Student("4", "������", "", 321702, d1, d2);
		Student student5 = new Student("5", "������", "", 321701, d1, d2);

		tableModel.addDate(student);
		tableModel.addDate(student1);
		tableModel.addDate(student2);
		tableModel.addDate(student3);
		tableModel.addDate(student4);
		tableModel.addDate(student5);

		table = new JTable(tableModel) {
			protected JTableHeader createDefaultTableHeader() {
				return new GroupableTableHeader(columnModel);
			}
		};

		new ColumnModel(table, numberExams);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(400, 400));
		frame.add(scrollPane);

		table.requestFocusInWindow();

	}

	void createToolBar(JFrame frame) {
		JToolBar fileToolBar = new JToolBar();
		JToolBar peopleToolBar = new JToolBar();
		JPanel transitionPage = new JPanel();

		fileToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		peopleToolBar.setLayout(new BoxLayout(peopleToolBar, BoxLayout.Y_AXIS));
		transitionPage.setLayout(new FlowLayout());

		fileToolBar.setFloatable(false);
		peopleToolBar.setFloatable(false);

		frame.add(fileToolBar, BorderLayout.NORTH);
		frame.add(peopleToolBar, BorderLayout.LINE_START);
		frame.add(transitionPage, BorderLayout.SOUTH);

		JLabel numberExamsLabel = new JLabel("   Number of Exams");
		JLabel numberRecordsLabel = new JLabel("   Number of Records");
		JLabel numberAvailableRecords = new JLabel(
				"    Number of the available records  ");
		JLabel numberAvailableRecordsLabel = new JLabel(
				String.valueOf(tableModel.getStudentList().size()));

		Color darkGreen = new Color(17, 111, 21);
		numberAvailableRecordsLabel.setForeground(Color.BLUE);
		numberAvailableRecordsLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		numberExamsLabel.setForeground(darkGreen);
		numberRecordsLabel.setForeground(darkGreen);
		numberAvailableRecords.setForeground(darkGreen);

		JButton saveButton = new JButton(
				new ImageIcon(Path.SAVE_ICON.getPath()));
		JButton openFileButton = new JButton(new ImageIcon(
				Path.OPEN_ICON.getPath()));
		JButton addPeopleButton = new JButton(new ImageIcon(
				Path.ADD_ICON.getPath()));
		JButton searchPeopleButton = new JButton(new ImageIcon(
				Path.SEARCH_ICON.getPath()));
		JButton deletePeopleButton = new JButton(new ImageIcon(
				Path.DELETE_ICON.getPath()));

		pageToggle = new PageToggle(transitionPage);
		saveButton.addActionListener(new FileSaver(studentList));
		openFileButton.addActionListener(new FileChooserListener(studentList, pageToggle, table));
		deletePeopleButton.setActionCommand("Remove");
		
		
		openFileItem.addActionListener(new FileChooserListener(studentList, pageToggle, table));

		fileToolBar.add(openFileButton);
		fileToolBar.add(saveButton);
		fileToolBar.add(numberExamsLabel);
		fileToolBar.add(numberExams);
		fileToolBar.add(numberRecordsLabel);
		fileToolBar.add(numberRecords);
		fileToolBar.add(numberAvailableRecords);
		fileToolBar.add(numberAvailableRecordsLabel);

		peopleToolBar.add(addPeopleButton);
		peopleToolBar.add(deletePeopleButton);
		peopleToolBar.add(searchPeopleButton);

		addPeopleItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ExamDialog(frame, table, (Integer) numberExams
						.getSelectedItem(), studentList, pageToggle);
			}
		});

		addPeopleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ExamDialog(frame, table, (Integer) numberExams
						.getSelectedItem(), studentList, pageToggle);
			}
		});

		searchPeopleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SearchDialog(frame, table, studentList, pageToggle);
			}
		});

		deletePeopleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SearchDialog(frame, table, studentList, pageToggle)
						.startRemove(true);
			}
		});

	
		
		numberRecords.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.setNumberRecords((Integer) numberRecords
						.getSelectedItem());
				// leftStartButton.doClick();
				pageToggle.getLeftStartButton().doClick();
			}
		});
		numberExams.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel = new ExamTableModel((Integer) numberExams
						.getSelectedItem());
				table.setModel(tableModel);
				new ColumnModel(table, (Integer) numberExams.getSelectedItem());
				tableModel.setStudentList(studentList);

				pageToggle.addButtonActionListener(table);

				numberRecords.setSelectedItem(numberRecords.getSelectedItem());
			}
		});

		table.addContainerListener(new ContainerListener() {

			private ExamTableModel model = (ExamTableModel) table.getModel();

			@Override
			public void componentRemoved(ContainerEvent e) {
				/*
				 * model = (ExamTableModel) table.getModel();
				 * numberAvailableRecordsLabel.setText(String.valueOf(model
				 * .getStudentList().size()));
				 */
				// pageToggle.addButtonActionListener(table);
				numberAvailableRecordsLabel.setText(String.valueOf(studentList
						.size()));
			}

			@Override
			public void componentAdded(ContainerEvent e) {
				/*
				 * model = (ExamTableModel) table.getModel();
				 * numberAvailableRecordsLabel.setText(String.valueOf(model
				 * .getStudentList().size()));
				 */
				// pageToggle.addButtonActionListener(table);
				numberAvailableRecordsLabel.setText(String.valueOf(studentList
						.size()));
			}
		});
		numberExams.setSelectedItem(2);
	}
}

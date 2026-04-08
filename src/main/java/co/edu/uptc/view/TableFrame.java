package co.edu.uptc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import co.edu.uptc.config.LoadConfig;
import co.edu.uptc.config.i18n.I18nConfig;
import co.edu.uptc.view.interfaces.Mapper;

public class TableFrame<T> extends JDialog {

    private JButton btnNext, btnPrevious;
    private DefaultTableModel tableModel;

    private List<T> list;
    private int currentPage, pageSize;

    private String[] cols;
    private Mapper<T> mapper;

    private final I18nConfig i18n;
    private final LoadConfig config;

    public TableFrame(JFrame parent, List<T> list, String[] cols, Mapper<T> mapper) {
        this(parent, list, cols, mapper, null);
    }

    public TableFrame(JFrame parent, List<T> list, String[] cols,
                      Mapper<T> mapper, String totalText) {
        super(parent, "Table", true);
        config   = LoadConfig.getInstance();
        i18n     = I18nConfig.getInstance();
        pageSize = Integer.parseInt(config.get("list.length"));
        currentPage = 0;
        this.list   = list;
        this.cols   = cols;
        this.mapper = mapper;
        initComponents(totalText);
        setupActions();
        loadPage();
        setVisible(true);
    }

    private void initComponents(String totalText) {
        setSize(520, 360);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        JTable table = buildTable();
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buildSouthPanel(totalText), BorderLayout.SOUTH);
    }

    private JTable buildTable() {
        JTable table = new JTable(tableModel);
        table.setRowHeight(26);
        table.setIntercellSpacing(new java.awt.Dimension(10, 4));
        table.setShowGrid(false);
        table.setFillsViewportHeight(true);

        styleHeader(table.getTableHeader());
        styleRows(table);
        return table;
    }

    private void styleHeader(JTableHeader header) {
        header.setBackground(new Color(0xF1EFE8));
        header.setForeground(new Color(0x5F5E5A));
        header.setFont(header.getFont().deriveFont(Font.BOLD, 12f));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xD3D1C7)));
    }

    private void styleRows(JTable table) {
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean selected, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, value, selected, focus, row, col);
                setHorizontalAlignment(LEFT);
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                if (!selected) {
                    setBackground(row % 2 == 0 ? Color.WHITE : new Color(0xF8F8F6));
                }
                return this;
            }
        };
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
        }
    }

    private JPanel buildSouthPanel(String totalText) {
        JPanel south = new JPanel(new BorderLayout());

        if (totalText != null) {
            south.add(buildTotalBar(totalText), BorderLayout.NORTH);
        }

        south.add(buildNavPanel(), BorderLayout.SOUTH);
        return south;
    }

    private JPanel buildTotalBar(String totalText) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 6));
        bar.setBackground(new Color(0xF1EFE8));
        bar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0xD3D1C7)));

        JLabel lbl = new JLabel(totalText);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 13f));
        lbl.setForeground(totalTextColor(totalText));
        bar.add(lbl);
        return bar;
    }

    private Color totalTextColor(String totalText) {
        try {
            String numPart = totalText.replaceAll("[^0-9.\\-]", "").trim();
            double val = Double.parseDouble(numPart);
            return val >= 0 ? new Color(0x0F6E56) : new Color(0xA32D2D);
        } catch (NumberFormatException e) {
            return new Color(0x444441);
        }
    }

    private JPanel buildNavPanel() {
        JPanel nav = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 6));
        btnPrevious = new JButton(i18n.get("btn.prev"));
        btnNext     = new JButton(i18n.get("btn.sig"));
        nav.add(btnPrevious);
        nav.add(btnNext);
        return nav;
    }

    private void setupActions() {
        btnNext.addActionListener(e     -> handleNext());
        btnPrevious.addActionListener(e -> handlePrevious());
    }

    private void handlePrevious() {
        if (currentPage > 0) { currentPage--; loadPage(); }
    }

    private void handleNext() {
        if ((currentPage + 1) * pageSize < list.size()) { currentPage++; loadPage(); }
    }

    private void loadPage() {
        tableModel.setRowCount(0);
        int start = currentPage * pageSize;
        int end   = Math.min(start + pageSize, list.size());
        for (int i = start; i < end; i++) {
            tableModel.addRow(mapper.map(list.get(i)));
        }
        updateButtons();
    }

    private void updateButtons() {
        btnPrevious.setEnabled(currentPage > 0);
        btnNext.setEnabled((currentPage + 1) * pageSize < list.size());
    }
}

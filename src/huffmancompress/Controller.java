/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmancompress;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author lenovo-zk
 */
public class Controller implements Initializable {

    @FXML
    private JFXTextField txtInput;
    @FXML
    private JFXButton btnCompress;
    @FXML
    private JFXTextArea txtAreaOrigin;
    @FXML
    private Label lblOrigin;
    @FXML
    private JFXTextArea txtAreaCompressed;
    @FXML
    private Label lblCompressed;
    @FXML
    private JFXTextArea txtAreaCodes;
    @FXML
    private JFXButton btnDecompress;
    @FXML
    private JFXTextArea txtAreaDecopressedData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnCompress.setOnAction(e->{
            String input = txtInput.getText();
            Huffman.buildHuffmanTree(input);
            txtAreaCodes.setText(Huffman.huffmanCodeGlobal.toString());
            txtAreaCompressed.setText(Huffman.encodedString.toString());
            lblCompressed.setText(String.valueOf(Huffman.encodedString.length()));
            txtAreaOrigin.setText(Huffman.strToBinary(input));
            lblOrigin.setText((String.valueOf(Huffman.strToBinary(input).length())));
        });
        
        btnDecompress.setOnAction(e->{
            if(!txtAreaCompressed.getText().isEmpty()){
                Huffman.Decompress(Huffman.encodedString);
                txtAreaDecopressedData.setText(Huffman.origin.toString());
            }
        });
        
    }    
    
}

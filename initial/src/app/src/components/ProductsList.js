import React, { Component } from "react";
import { connect } from "react-redux";
import {
    getProducts,
    getProduct,
    deleteProduct,
    updateProduct
} from "../actions/productActions";
import FontAwesome from 'react-fontawesome';

import { Button, Table ,ListGroup, ListGroupItem,Modal, ModalHeader,ModalBody } from "react-bootstrap";


class ProductsList extends Component {
    constructor(props) {
        super(props);
        this.onFailure = this.onFailure.bind(this)
        this.onSuccess = this.onSuccess.bind(this);
        this.onFailure = this.onFailure.bind(this);
        this.onSuccess = this.onSuccess.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.state = {
            showModal: false,
            modalMsg: '',
            modalMsgHeader: '',

        };
    }

    async componentWillMount() {
        //this.props.getProducts(this.onfailure);
        await   this.props.getProducts(this.onfailure);
    }

    componentWillReceiveProps(nextProps) {
    }

    onSuccess() {
        this.setState({
            showModal: true,
            modalMsg: 'your changes was affected successfully',
            modalMsgHeader: 'Congratulations!'
        });
    }

    onFailure(msg) {
        this.setState({showModal: true, modalMsg: msg, modalMsgHeader: 'Failure!!'});
    }

    onDelete(item) {

        this.props.deleteProduct(item, this.onSuccess, this.onFailure);
    }

    onEdit(item) {

        this.props.getProduct(item, this.onFailure);

    }

    handleClose() {
        this.setState({
            showModal: false,
            modalMsg: '',
            modalMsgHeader: '',
        });
    }

    render() {

        const categories = {
            Electronic : 'Electronic.png',


        }
        return (

            <div className="table-panel">

                <Modal show={this.state.showModal} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>{this.state.modalMsgHeader}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <h4>{this.state.modalMsg}</h4>
                    </Modal.Body>
                </Modal>
                <div>
                    <h4 style={{textAlign:"center", color:"#f5f5f5"}}> Products List</h4>
                </div>

                <ListGroup>

                    {this.props.products.map((item, key) => {
                        return (
                            <ListGroupItem header={item.name} key={key}>


                                <div className="col-md-8">
                                    {item.description}
                                </div>
                                <div className="col-md-1">
                                    $  {item.amount}
                                </div>
                                <div className="col-md-1">

                                    <img
                                        style={{marginTop:'-30px'}}
                                        height="45px"
                                        className="product-icon"
                                        src={`/assets/img/${item.category}.png`}
                                        alt={item.category}
                                    />
                                </div>
                                <div className="col-md-1" style={{textAlign:"center"}}>
                                    <i onClick={this.onEdit.bind(this, item)} className="fa fa-edit"
                                       style={{cursor:'pointer', fontSize: '35px' , color:'orange',marginTop:"-7px"}}/>
                                </div>
                                <div className="col-md-1" style={{textAlign:"center"}}>
                                    <i onClick={this.onDelete.bind(this, item)} className="fa fa-trash"
                                       style={{cursor:'pointer',  fontSize: '35px' , color:'red', marginTop:"-7px"}}/>
                                </div>


                            </ListGroupItem>
                        );
                    })}

                </ListGroup>


            </div>
        );
    }
}
const mapStateToProps = state => ({
    products: state.reducer.products,
    newProduct: state.reducer.product
});
export default connect(
    mapStateToProps,
    {getProducts, getProduct, deleteProduct, updateProduct}
)(ProductsList);

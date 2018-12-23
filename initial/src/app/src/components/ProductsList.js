import React, { Component } from "react";
import { connect } from "react-redux";
import {
    getProducts,
    getProduct,
    deleteProduct,
    updateProduct
} from "../actions/productActions";
import FontAwesome from 'react-fontawesome';

import { Button, Table ,ListGroup, ListGroupItem } from "react-bootstrap";


class ProductsList extends Component {
    constructor(props) {
        super(props);
        this.onfailure = this.onfailure.bind(this)
        this.onSuccess = this.onSuccess.bind(this)
    }

    componentWillMount() {
        this.props.getProducts(this.onfailure);
    }

    componentWillReceiveProps(nextProps) {
    }

    onfailure() {

    }

    onSuccess() {

    }

    onDelete(item) {
        this.props.deleteProduct(item, this.onSuccess, this.onfailure);
    }

    onEdit(item) {
        this.props.getProduct(item, this.onfailure);
    }

    render() {
        return (

            <div className="table-panel">
                <div>
                    <h4 style={{textAlign:"center", color:"#f5f5f5"}}> Products List

                    </h4>
                </div>

                <ListGroup>

                    {this.props.products.map((item, key) => {
                        return (
                            <ListGroupItem header={item.name} key={key}>


                                <div className="col-md-8">
                                    {item.description}
                                </div>
                                <div className="col-md-1">
                                    {item.amount} $
                                </div>
                                <div className="col-md-1">
                                    img
                                </div>
                                <div className="col-md-1">
                                    <i onClick={this.onEdit.bind(this, item)} className="fa fa-edit"
                                       style={{cursor:'pointer', fontSize: '35px' , color:'orange'}}/>
                                </div>
                                <div className="col-md-1">
                                    <i onClick={this.onDelete.bind(this, item)} className="fa fa-trash"
                                       style={{cursor:'pointer',  fontSize: '35px' , color:'red'}}/>
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

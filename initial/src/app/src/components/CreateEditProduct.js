import React, { Component } from "react";
import { Button, Modal, ModalHeader,ModalBody } from "react-bootstrap";
import { connect } from "react-redux";
import { createProduct, updateProduct } from "../actions/productActions";
import moment from "moment";

class CreateEditProduct extends Component {
    constructor(props) {
        super(props);
        this.onChange = this.onChange.bind(this);
        this.onSave = this.onSave.bind(this);
        this.onReset = this.onReset.bind(this);
        this.onFailure = this.onFailure.bind(this);
        this.onSuccess = this.onSuccess.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);

        if (this.props.product.id) {
            //this.setState({showModal:true});
            this.state = {
                showModal: true,
            }
        }
        this.state = {
            showModal: false,
            modalMsg: '',
            modalMsgHeader: '',
            name: "",
            description: "",
            type: "",
            amount: 0,

        };
    }

    handleClose() {
        this.setState({showModal: false});
    }

    handleShow() {
        this.setState({showModal: true});
    }

    onChange(e) {
        this.setState({[e.target.name]: e.target.value});
    }

    componentWillMount() {
        if (this.props.product.id) {
            this.setState({showModal: true});
        }
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.product && nextProps.product.id) {
            this.setState(nextProps.product);
        }
        if (this.props.product.id) {
            this.setState({showModal: true});
        }
    }

    onReset(e) {
        if (e) e.preventDefault();
        this.setState({
            showModal: false,
            modalMsg: '',
            modalMsgHeader: '',
            name: "",
            description: "",
            type: "",
            amount: 0,
            id: "",
            date: moment().format("YYYY/MM/DD hh:mm:ss")
        });
    }

    onSave(e) {
        e.preventDefault();
        const product = {
            name: this.state.name,
            description: this.state.description,
            type: this.state.type ? this.state.type : "veg",
            amount: this.state.amount,
            date: moment().format("YYYY/MM/DD hh:mm:ss")
        };
        if (this.state.id) {
            product["id"] = this.state.id;
            this.props.updateProduct(
                product,
                this.onSuccess,
                this.onFailure
            );
        } else {
            this.props.createProduct(
                product,
                this.onSuccess,
                this.onFailure
            );
        }
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

    render() {

        return (

            <div className="createProduct">
                <i onClick={this.handleShow} className="fa fa-plus-circle"
                   style={{ fontSize: '37px' , color:'#79e079'}}/>
                <div className="panel panel-default  ">


                    <Modal show={this.state.showModal} onHide={this.handleClose}>
                        <Modal.Header closeButton>
                            <Modal.Title>{this.state.modalMsgHeader}</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <h4>{this.state.modalMsg}</h4>

                            <form className="panel-body">
                                <div className="form-row">
                                    <div className="form-group col-md-6">
                                        <label htmlFor="name">Product Name</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            name="name"
                                            id="name"
                                            value={this.state.name}
                                            placeholder="Product Name"
                                            onChange={this.onChange}
                                        />
                                    </div>
                                    <div className="form-group col-md-6">
                                        <label htmlFor="amount">Amount</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="amount"
                                            value={this.state.amount}
                                            name="amount"
                                            placeholder="Amount"
                                            onChange={this.onChange}
                                        />
                                    </div>
                                </div>

                                <div className="form-row">
                                    <div className="form-group col-md-6">
                                        <label htmlFor="description">Description</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="description"
                                            name="description"
                                            value={this.state.description}
                                            placeholder="Describe this product ..."
                                            onChange={this.onChange}
                                        />
                                    </div>
                                    <div className="form-group col-md-6">
                                        <label htmlFor="type">Category</label>
                                        <select
                                            defaultValue={this.state.type}
                                            onChange={this.onChange}
                                            id="type"
                                            name="type"
                                            className="form-control"
                                        >
                                            <option>Choose...</option>
                                            {["Electronic", "Health And Beauty", "Pet", "Home And Furniture", "other"].map(
                                                type => {
                                                    return (
                                                        <option
                                                            key={type}
                                                            value={type}
                                                            selected={this.state.type === type}
                                                        >
                                                            {type}
                                                        </option>
                                                    );
                                                }
                                            )}
                                        </select>
                                    </div>
                                    <div className="form-group col-md-12">

                                        <Button style={{margin:"5px"}}
                                                onClick={this.onSave}
                                                className="btn btn-success col-md-1"
                                        >
                                            Save
                                        </Button>


                                        <Button style={{margin:"5px"}}
                                                onClick={this.onReset}
                                                className="btn btn-danger col-md-1"
                                        >
                                            Reset
                                        </Button>
                                    </div>
                                </div>
                            </form>


                        </Modal.Body>
                    </Modal>

                </div>
            </div>
        );
    }
}

const mapStateToProps = state => ({
    product: state.reducer.product
});
export default connect(
    mapStateToProps,
    {createProduct, updateProduct}
)(CreateEditProduct);

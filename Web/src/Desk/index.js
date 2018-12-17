import React, { Component } from 'react';
import Modal from 'react-modal';
import { HttpUtil } from '../Common/HttpUtil';
import './index.css';

export default class Desk extends Component {
  constructor(props) {
    super(props);
    this._selectDesk.bind(this);
    this.state = {
      desk: undefined,
      showModal: false,
    }
  }

  componentDidMount() {
    this._loadDeskData();
  }

  _loadDeskData = () => {
    HttpUtil.post('desk', { a: 123 }).then((result) => {
      console.log(result);
      const { data } = result;
      this.setState({ desk: data });
    }).catch((err) => {
      console.log(err);
    });
  }

  _selectDesk = () => {
    console.log('=============', this);
    this.setState({
      showModal: true,
    });
  }

  _renderModal = () => {
    console.log('======_renderModal=======', this, this.state.showModal);
    return (
      <Modal
        className="modal"
        overlayClassName="Overlay"
        isOpen={this.state.showModal}
        contentLabel="Minimal Modal Example"
      >
        <button
          onClick={() => {
            this.setState({
              showModal: false,
            })
          }}>
          Close Modal
          </button>
      </Modal>
    );
  }

  render() {
    const { desk } = this.state;
    const { list } = desk || {};
    if (desk) {
      return (
        <div className="deskContainer">
          <div>
            <div>桌号：{desk.deskNo}</div>
            <button onClick={this._selectDesk}>选择牌桌</button>
          </div>
          {this._renderModal()}
        </div>
      );
    }
    return (
      <div className="deskContainer">
        <p>数据请求出错</p>
      </div>
    );

  }
}

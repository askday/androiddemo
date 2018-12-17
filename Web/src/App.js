import React, { Component } from 'react';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import "react-tabs/style/react-tabs.css";
import './App.css';
import Home from './Home';
import Desk from './Desk';
// import MakeData from './MakeData';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = { tabIndex: 0 };
  }

  render() {
    const { data } = this;
    return (
      <div className="App">
        <Tabs
          selectedIndex={this.state.tabIndex}
          onSelect={tabIndex => {
            this.setState({ tabIndex });
          }}>
          <TabList>
            <Tab>数据</Tab>
            <Tab>牌桌</Tab>
          </TabList>
          <TabPanel forceRender>
            <Home />
          </TabPanel>
          <TabPanel forceRender>
            <Desk />
          </TabPanel>
        </Tabs>
      </div>
    );
  }
}

export default App;

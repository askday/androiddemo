function generateData(root, dept) {
  const count = Math.floor(Math.random() * 10);

  for (let i = 0; i < count; i++) {
    const children = [];
    if (dept - 1 > 0) {
      generateData(children, dept - 1);
    }
    if (children.length > 0) {
      const obj = {
        name: '@cword(3,8)',
        category: dept * i,
        children,
      }
      root.push(obj);
    } else {
      const obj = {
        name: '@cword(3,8)',
        category: dept * i,
        children
      }
      root.push(obj);
    }
  }
}

module.exports = {
  'list': {
    response: (req) => {
      const data = [];
      generateData(data, 3);
      return {
        retcode: 200,
        retdesc: 'success',
        data,
      };
    }
  },
  'grid': {
    response: (req) => {
      const data = [];
      for (let i = 0; i < 169; i++) {
        const isUnAssigned = Math.random() > 0.2 ? false : true;
        let unassigned = 0.0;
        let bb = 0.0;
        let cell = 0.0;
        let fold = 0.0;
        if (isUnAssigned) {
          unassigned = 1.0;
        } else {
          bb = Math.random();
          cell = (1 - bb) * Math.random();
          fold = 1 - bb - cell;
        }
        data.push({ name: '@word(1,3)', bb, cell, fold, unassigned });
      }
      return {
        retcode: 200,
        retdesc: 'success',
        data,
      };
    }
  },
  'desk': {
    response: (req) => {
      const reqParams = req.body || {};
      console.log(reqParams);
      const list = [];
      const count = Math.random() * 5;
      for (let i = 0; i < count; i++) {
        list.push({
          no: i,
          name: '@word(5,9)',
          money: 100.00,
        });
      }
      return {
        retcode: 200,
        retdesc: 'success',
        data: {
          'deskNo|1-100': 10,
          list,
        }
      };
    }
  },
  'desklist': {
    response: (req) => {
      const reqParams = req.body || {};
      console.log(reqParams);
      const data = [];
      for (let m = 0; m < 50; m++) {
        const list = [];
        const count = Math.random() * 5;
        for (let i = 0; i < count; i++) {
          list.push({
            no: i,
            name: '@word(5,9)',
            money: 100.00,
          });
        }
        data.push({
          'deskNo|1-100': 10,
          list,
        });
      }
      return {
        retcode: 200,
        retdesc: 'success',
        data,
      };
    }
  },
  'submit': {
    response: (req) => {
      const reqParams = req.body || {};
      console.log(reqParams);
      return {
        retcode: 200,
        retdesc: 'success',
      };
    }
  }
};
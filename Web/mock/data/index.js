function generateData(root, dept) {
  const count = Math.floor(Math.random() * 10);
  for (let i = 0; i < count; i++) {
    const info = [];

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
      info.push({ name: '@word(1,3)', bb, cell, fold, unassigned });
    }

    const children = [];
    if (dept - 1 > 0) {
      generateData(children, dept - 1);
    }
    const obj = {
      name: '@cword(3,8)',
      info,
      children,
    }
    root.push(obj);
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
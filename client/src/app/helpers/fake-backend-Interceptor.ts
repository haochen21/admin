import { Injectable } from '@angular/core';
import { HttpRequest, HttpResponse, HttpHandler, HttpEvent, HttpInterceptor, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { delay, mergeMap, materialize, dematerialize } from 'rxjs/operators';

@Injectable()
export class FakeBackendInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const { url, method, headers, body } = request;

    // wrap in delayed observable to simulate server api call
    return of(null)
      .pipe(mergeMap(handleRoute))
      .pipe(materialize()) // call materialize and dematerialize to ensure delay even if an error is thrown (https://github.com/Reactive-Extensions/RxJS/issues/648)
      .pipe(delay(500))
      .pipe(dematerialize());

    function handleRoute() {
      switch (true) {
        case url.endsWith('/auth/signin') && method === 'POST':
          return authenticate();
        case url.endsWith('/merchant/page') && method === 'POST':
          return getMerchants();
        case url.match(/\/merchant\/\d+$/) && method === 'GET':
          return getMerchant();
        case url.match(/\/merchant\/\d+$/) && method === 'PUT':
          return getMerchant();
        case url.endsWith('/api/order/page') && method === 'POST':
          return getOrders();
        case url.endsWith('/api/bill/page') && method === 'POST':
          return getBills();
        case url.endsWith('/api/agentBill/page') && method === 'POST':
          return getAgentBills();
        case url.endsWith('/api/bill/statTicketEarning') && method === 'POST':
          return earning();
        case url.endsWith('/api/agent/page') && method === 'POST':
          return getAgentPage();
        case url.endsWith('/api/user/agent') && method === 'POST':
          return findAgents();
        case url.match(/\/agent\/\d+$/) && method === 'GET':
          return findAgent();
        case url.match(/\/agent\/\d+$/) && method === 'PUT':
          return findAgent();
        case url.endsWith('/api/agent') && method === 'POST':
          return findAgent();
        case url.startsWith('/api/user/password') && method === 'PUT':
          return findAgent();
        default:
          // pass through any requests not handled above
          return next.handle(request);
      }
    }

    // route functions

    function authenticate() {
      return ok({
        token: 'fake-jwt-token'
      })
    }

    function getMerchants() {
      return ok({
        "content": [
          {
            "id": 1000,
            "loginName": "追梦者",
            "deviceNo": null,
            "printNo": "4012148",
            "approved": true,
            "openId": "oyA2pjqoMfYFIjfB88IArVasfAy4",
            "transferOpenId": "oV3NltzYQuKkZ3KrAaf49U-CBcKI",
            "name": "康萌预约宝",
            "realName": null,
            "phone": "‭1234567890",
            "mail": "123456789@1256.com",
            "open": true,
            "discount": 1,
            "takeByPhone": true,
            "takeByPhoneSuffix": true,
            "rate": 0.01,
            "autoPayment": true,
            "trash": null,
            "takeOut": true,
            "user": null
          },
          {
            "id": 4009548,
            "loginName": "陈昊",
            "deviceNo": "12345",
            "printNo": "1896",
            "approved": true,
            "openId": "oyA2pjnuf-oFDaZi0BwUlZ_XsX2U",
            "transferOpenId": null,
            "name": "陈昊店铺",
            "realName": null,
            "phone": "13817475681",
            "mail": "chenhao21@163.com",
            "open": true,
            "discount": 1,
            "takeByPhone": true,
            "takeByPhoneSuffix": true,
            "rate": null,
            "autoPayment": null,
            "trash": null,
            "takeOut": true,
            "user": null
          },
          {
            "id": 4009549,
            "loginName": "园耕头",
            "deviceNo": "36074020000425",
            "printNo": "222,888",
            "approved": true,
            "openId": "oyA2pjhDkSB8l2SE3PiseGPuKa2Q",
            "transferOpenId": "oV3Nlt-UU8Ol87XADYsYXVEn5LnA",
            "name": "康萌预约宝展示账户",
            "realName": null,
            "phone": "15618495256",
            "mail": "1377008716@qq.com",
            "open": true,
            "discount": 1,
            "takeByPhone": true,
            "takeByPhoneSuffix": true,
            "rate": 0.01,
            "autoPayment": true,
            "trash": null,
            "takeOut": false,
            "user": null
          },
          {
            "id": 4009651,
            "loginName": "康萌预约宝",
            "deviceNo": "26104020000001",
            "printNo": "222,888",
            "approved": true,
            "openId": "oyA2pjsrFh7zPIXOKpJ22zfn4Bb0",
            "transferOpenId": "oV3Nlt-UU8Ol87XADYsYXVEn5LnA",
            "name": "奉贤水果团购",
            "realName": null,
            "phone": "13296128989",
            "mail": "2638192745@qq.com",
            "open": true,
            "discount": null,
            "takeByPhone": true,
            "takeByPhoneSuffix": true,
            "rate": 0.01,
            "autoPayment": true,
            "trash": null,
            "takeOut": null,
            "user": null
          },
          {
            "id": 4009851,
            "loginName": "a！乌鸦",
            "deviceNo": null,
            "printNo": "4009851",
            "approved": true,
            "openId": "oyA2pjpJpFrqXCeyUjgY8vSVxrgw",
            "transferOpenId": "oV3Nlt9TMpJx_rLRe8KreLDpgVYI",
            "name": "华裕餐饮（外卖）",
            "realName": null,
            "phone": "17356312577",
            "mail": "312620257@qq.com",
            "open": true,
            "discount": null,
            "takeByPhone": true,
            "takeByPhoneSuffix": true,
            "rate": 0.01,
            "autoPayment": true,
            "trash": null,
            "takeOut": true,
            "user": null
          },
          {
            "id": 4009903,
            "loginName": "A海",
            "deviceNo": null,
            "printNo": null,
            "approved": true,
            "openId": "oyA2pjiJXTKz75OUbUgyxL8GqU8k",
            "transferOpenId": "oV3Nlt3FjOgapnQuv7tObcMyhs6Y",
            "name": "松江新城水站",
            "realName": null,
            "phone": "18221618315",
            "mail": "289465984@qq.com",
            "open": true,
            "discount": null,
            "takeByPhone": true,
            "takeByPhoneSuffix": true,
            "rate": 0.01,
            "autoPayment": true,
            "trash": null,
            "takeOut": true,
            "user": null
          },
          {
            "id": 4010361,
            "loginName": "su su",
            "deviceNo": null,
            "printNo": "4010361",
            "approved": true,
            "openId": "oyA2pjtZ3T0EZP-Ew0Chq24V1f1A",
            "transferOpenId": "oV3Nlt4oe5ynynXlD-Hmuowl8tfA",
            "name": "果树林水果店（外送）",
            "realName": null,
            "phone": "18217183363",
            "mail": "sulicheng@hotmail.com",
            "open": true,
            "discount": null,
            "takeByPhone": true,
            "takeByPhoneSuffix": true,
            "rate": 0.01,
            "autoPayment": true,
            "trash": null,
            "takeOut": true,
            "user": null
          },
          {
            "id": 4010363,
            "loginName": "小辣椒菜馆",
            "deviceNo": null,
            "printNo": null,
            "approved": true,
            "openId": "oyA2pjnJzyGzRxTpdsOSlwB5F9k0",
            "transferOpenId": null,
            "name": null,
            "realName": null,
            "phone": null,
            "mail": null,
            "open": true,
            "discount": 1,
            "takeByPhone": null,
            "takeByPhoneSuffix": null,
            "rate": null,
            "autoPayment": null,
            "trash": null,
            "takeOut": null,
            "user": null
          },
          {
            "id": 4010682,
            "loginName": "随缘",
            "deviceNo": null,
            "printNo": null,
            "approved": true,
            "openId": "oyA2pjtfUV4blw0FOUvXgKQY-LOc",
            "transferOpenId": null,
            "name": null,
            "realName": null,
            "phone": null,
            "mail": null,
            "open": true,
            "discount": 1,
            "takeByPhone": null,
            "takeByPhoneSuffix": null,
            "rate": null,
            "autoPayment": null,
            "trash": null,
            "takeOut": null,
            "user": null
          },
          {
            "id": 4016606,
            "loginName": "周洋",
            "deviceNo": null,
            "printNo": "4016606,888",
            "approved": true,
            "openId": "oyA2pjmzFlQLvgbP1lxTESLl6K0A",
            "transferOpenId": "oV3Nlt2Q6eJxbn0HaMi2DvzvAx2k",
            "name": "吕瑞江私房牛肉面",
            "realName": "周洋",
            "phone": "15258910697",
            "mail": "1074382158@qq.com",
            "open": true,
            "discount": 1.0,
            "takeByPhone": true,
            "takeByPhoneSuffix": true,
            "rate": 0.02,
            "autoPayment": true,
            "trash": null,
            "takeOut": null,
            "user": {
              "id": 736,
              "name": "祖广祥",
              "loginName": "zgx",
              "password": "$2a$10$48jApaeS/yRIeMcUma5uzuH.k1mcXwIIbiZRTzCtWKQXuDSKPztRW",
              "phone": "17731535622",
              "email": "17731535622@qq.com",
              "profile": 1,
              "rate": 0.005,
              "transferOpenId": "oV3Nlt4LKLYV0zFR86lULgI9gJGg",
              "merchants": null,
              "version": 1,
              "authorities": null
            }
          }
        ],
        "last": false,
        "totalPages": 6,
        "totalElements": 53,
        "numberOfElements": 10,
        "sort": null,
        "first": true,
        "size": 10,
        "number": 0
      });

    }

    function getMerchant() {
      return ok({
        "id": 4016606,
        "loginName": "周洋",
        "deviceNo": null,
        "printNo": "4016606,888",
        "approved": true,
        "openId": "oyA2pjmzFlQLvgbP1lxTESLl6K0A",
        "transferOpenId": "oV3Nlt2Q6eJxbn0HaMi2DvzvAx2k",
        "name": "吕瑞江私房牛肉面",
        "realName": "周洋",
        "phone": "15258910697",
        "mail": "1074382158@qq.com",
        "open": true,
        "discount": 1.0,
        "takeByPhone": true,
        "takeByPhoneSuffix": true,
        "rate": 0.02,
        "autoPayment": true,
        "trash": null,
        "takeOut": null,
        "user": {
          "id": 736,
          "name": "祖广祥",
          "loginName": "zgx",
          "password": "$2a$10$48jApaeS/yRIeMcUma5uzuH.k1mcXwIIbiZRTzCtWKQXuDSKPztRW",
          "phone": "17731535622",
          "email": "17731535622@qq.com",
          "profile": 1,
          "rate": 0.005,
          "transferOpenId": "oV3Nlt4LKLYV0zFR86lULgI9gJGg",
          "merchants": null,
          "version": 1,
          "authorities": null
        }
      });
    }

    function findAgents() {
      return ok([
        {
          "id": 41,
          "name": "陈昊01",
          "loginName": "chenhao01",
          "password": "$2a$10$AbrdbERG7ig1xIbk3eDNFOPoDthpWM8Lmf7nRY0rfATf8fZBjwhTS",
          "phone": "13223432345",
          "email": "chenhao01@163.com",
          "profile": 1,
          "rate": null,
          "transferOpenId": null,
          "merchants": null,
          "version": 0,
          "authorities": null
        }
      ]);
    }

    function getOrders() {
      return ok({
        "content": [
          {
            "id": 4016787,
            "no": "5cef42c1d6ab53509f354931",
            "transactionId": "4200000307201905303837085699",
            "merchant": {
              "id": 4016606,
              "loginName": "周洋",
              "deviceNo": null,
              "printNo": "4016606,888",
              "approved": true,
              "openId": "oyA2pjmzFlQLvgbP1lxTESLl6K0A",
              "transferOpenId": "oV3Nlt2Q6eJxbn0HaMi2DvzvAx2k",
              "name": "吕瑞江私房牛肉面",
              "realName": "周洋",
              "phone": "15258910697",
              "mail": "1074382158@qq.com",
              "open": true,
              "discount": 1.0,
              "takeByPhone": true,
              "takeByPhoneSuffix": true,
              "rate": 0.02,
              "autoPayment": true,
              "trash": null,
              "takeOut": null,
              "user": null
            },
            "customer": {
              "id": 4016378,
              "loginName": "做人要淡定。",
              "openId": "oV3Nlt4LKLYV0zFR86lULgI9gJGg",
              "name": "做人要淡定。",
              "cardNo": null,
              "phone": "17731535622",
              "mail": null,
              "createdOn": 1557712366000
            },
            "status": 3,
            "totalPrice": 14.00,
            "payTime": 1559184666000,
            "createdOn": 1559184066000,
            "remark": null,
            "cartItems": [
              {
                "id": 4016785,
                "name": "番茄蛋炒饭",
                "quantity": 1,
                "unitPrice": 9.00,
                "totalPrice": 9.00,
                "product": null
              },
              {
                "id": 4016791,
                "name": "加鸡腿1个",
                "quantity": 1,
                "unitPrice": 5.00,
                "totalPrice": 5.00,
                "product": null
              }
            ]
          },
          {
            "id": 4016747,
            "no": "5cec96b6d6ab53509f354927",
            "transactionId": "4200000298201905281862225314",
            "merchant": {
              "id": 4016606,
              "loginName": "周洋",
              "deviceNo": null,
              "printNo": "4016606,888",
              "approved": true,
              "openId": "oyA2pjmzFlQLvgbP1lxTESLl6K0A",
              "transferOpenId": "oV3Nlt2Q6eJxbn0HaMi2DvzvAx2k",
              "name": "吕瑞江私房牛肉面",
              "realName": "周洋",
              "phone": "15258910697",
              "mail": "1074382158@qq.com",
              "open": true,
              "discount": 1.0,
              "takeByPhone": true,
              "takeByPhoneSuffix": true,
              "rate": 0.02,
              "autoPayment": true,
              "trash": null,
              "takeOut": null,
              "user": null
            },
            "customer": {
              "id": 4016343,
              "loginName": "爆米花",
              "openId": "oV3Nlt4lvutSgkYAkrdW_IEd0Icc",
              "name": "爆米花",
              "cardNo": null,
              "phone": "1773153535622",
              "mail": null,
              "createdOn": 1557234019000
            },
            "status": 3,
            "totalPrice": 0.01,
            "payTime": 1559009551000,
            "createdOn": 1559008951000,
            "remark": null,
            "cartItems": [
              {
                "id": 4016752,
                "name": "测试 商品",
                "quantity": 1,
                "unitPrice": 0.01,
                "totalPrice": 0.01,
                "product": null
              }
            ]
          },
          {
            "id": 4016746,
            "no": "5cec956bd6ab53509f354926",
            "transactionId": "4200000305201905289582231569",
            "merchant": {
              "id": 4016606,
              "loginName": "周洋",
              "deviceNo": null,
              "printNo": "4016606,888",
              "approved": true,
              "openId": "oyA2pjmzFlQLvgbP1lxTESLl6K0A",
              "transferOpenId": "oV3Nlt2Q6eJxbn0HaMi2DvzvAx2k",
              "name": "吕瑞江私房牛肉面",
              "realName": "周洋",
              "phone": "15258910697",
              "mail": "1074382158@qq.com",
              "open": true,
              "discount": 1.0,
              "takeByPhone": true,
              "takeByPhoneSuffix": true,
              "rate": 0.02,
              "autoPayment": true,
              "trash": null,
              "takeOut": null,
              "user": null
            },
            "customer": {
              "id": 4016343,
              "loginName": "爆米花",
              "openId": "oV3Nlt4lvutSgkYAkrdW_IEd0Icc",
              "name": "爆米花",
              "cardNo": null,
              "phone": "1773153535622",
              "mail": null,
              "createdOn": 1557234019000
            },
            "status": 3,
            "totalPrice": 0.01,
            "payTime": 1559009219000,
            "createdOn": 1559008619000,
            "remark": null,
            "cartItems": [
              {
                "id": 4016751,
                "name": "测试 商品",
                "quantity": 1,
                "unitPrice": 0.01,
                "totalPrice": 0.01,
                "product": null
              }
            ]
          },
          {
            "id": 4016740,
            "no": "5cec9535d6ab53509f354925",
            "transactionId": "4200000300201905287880769008",
            "merchant": {
              "id": 4016606,
              "loginName": "周洋",
              "deviceNo": null,
              "printNo": "4016606,888",
              "approved": true,
              "openId": "oyA2pjmzFlQLvgbP1lxTESLl6K0A",
              "transferOpenId": "oV3Nlt2Q6eJxbn0HaMi2DvzvAx2k",
              "name": "吕瑞江私房牛肉面",
              "realName": "周洋",
              "phone": "15258910697",
              "mail": "1074382158@qq.com",
              "open": true,
              "discount": 1.0,
              "takeByPhone": true,
              "takeByPhoneSuffix": true,
              "rate": 0.02,
              "autoPayment": true,
              "trash": null,
              "takeOut": null,
              "user": null
            },
            "customer": {
              "id": 4016343,
              "loginName": "爆米花",
              "openId": "oV3Nlt4lvutSgkYAkrdW_IEd0Icc",
              "name": "爆米花",
              "cardNo": null,
              "phone": "1773153535622",
              "mail": null,
              "createdOn": 1557234019000
            },
            "status": 3,
            "totalPrice": 0.01,
            "payTime": 1559009166000,
            "createdOn": 1559008566000,
            "remark": null,
            "cartItems": [
              {
                "id": 4016745,
                "name": "测试 商品",
                "quantity": 1,
                "unitPrice": 0.01,
                "totalPrice": 0.01,
                "product": null
              }
            ]
          },
          {
            "id": 4016739,
            "no": "5cec94f4d6ab53509f354924",
            "transactionId": "4200000289201905287961824033",
            "merchant": {
              "id": 4016606,
              "loginName": "周洋",
              "deviceNo": null,
              "printNo": "4016606,888",
              "approved": true,
              "openId": "oyA2pjmzFlQLvgbP1lxTESLl6K0A",
              "transferOpenId": "oV3Nlt2Q6eJxbn0HaMi2DvzvAx2k",
              "name": "吕瑞江私房牛肉面",
              "realName": "周洋",
              "phone": "15258910697",
              "mail": "1074382158@qq.com",
              "open": true,
              "discount": 1.0,
              "takeByPhone": true,
              "takeByPhoneSuffix": true,
              "rate": 0.02,
              "autoPayment": true,
              "trash": null,
              "takeOut": null,
              "user": null
            },
            "customer": {
              "id": 4016343,
              "loginName": "爆米花",
              "openId": "oV3Nlt4lvutSgkYAkrdW_IEd0Icc",
              "name": "爆米花",
              "cardNo": null,
              "phone": "1773153535622",
              "mail": null,
              "createdOn": 1557234019000
            },
            "status": 3,
            "totalPrice": 0.01,
            "payTime": 1559009101000,
            "createdOn": 1559008501000,
            "remark": null,
            "cartItems": [
              {
                "id": 4016744,
                "name": "测试 商品",
                "quantity": 1,
                "unitPrice": 0.01,
                "totalPrice": 0.01,
                "product": null
              }
            ]
          },
          {
            "id": 4016738,
            "no": "5cec944ad6ab53509f354923",
            "transactionId": "4200000305201905289663546559",
            "merchant": {
              "id": 4016606,
              "loginName": "周洋",
              "deviceNo": null,
              "printNo": "4016606,888",
              "approved": true,
              "openId": "oyA2pjmzFlQLvgbP1lxTESLl6K0A",
              "transferOpenId": "oV3Nlt2Q6eJxbn0HaMi2DvzvAx2k",
              "name": "吕瑞江私房牛肉面",
              "realName": "周洋",
              "phone": "15258910697",
              "mail": "1074382158@qq.com",
              "open": true,
              "discount": 1.0,
              "takeByPhone": true,
              "takeByPhoneSuffix": true,
              "rate": 0.02,
              "autoPayment": true,
              "trash": null,
              "takeOut": null,
              "user": null
            },
            "customer": {
              "id": 4016343,
              "loginName": "爆米花",
              "openId": "oV3Nlt4lvutSgkYAkrdW_IEd0Icc",
              "name": "爆米花",
              "cardNo": null,
              "phone": "1773153535622",
              "mail": null,
              "createdOn": 1557234019000
            },
            "status": 3,
            "totalPrice": 0.01,
            "payTime": 1559008930000,
            "createdOn": 1559008330000,
            "remark": null,
            "cartItems": [
              {
                "id": 4016743,
                "name": "测试 商品",
                "quantity": 1,
                "unitPrice": 0.01,
                "totalPrice": 0.01,
                "product": null
              }
            ]
          },
          {
            "id": 4016737,
            "no": "5cec92a3d6ab53509f354922",
            "transactionId": "4200000308201905285245758585",
            "merchant": {
              "id": 4016606,
              "loginName": "周洋",
              "deviceNo": null,
              "printNo": "4016606,888",
              "approved": true,
              "openId": "oyA2pjmzFlQLvgbP1lxTESLl6K0A",
              "transferOpenId": "oV3Nlt2Q6eJxbn0HaMi2DvzvAx2k",
              "name": "吕瑞江私房牛肉面",
              "realName": "周洋",
              "phone": "15258910697",
              "mail": "1074382158@qq.com",
              "open": true,
              "discount": 1.0,
              "takeByPhone": true,
              "takeByPhoneSuffix": true,
              "rate": 0.02,
              "autoPayment": true,
              "trash": null,
              "takeOut": null,
              "user": null
            },
            "customer": {
              "id": 4012032,
              "loginName": "康萌",
              "openId": "oV3Nlt-UU8Ol87XADYsYXVEn5LnA",
              "name": "康萌",
              "cardNo": "1690998219",
              "phone": "15618495256",
              "mail": null,
              "createdOn": 1505136292000
            },
            "status": 4,
            "totalPrice": 0.01,
            "payTime": 1559008507000,
            "createdOn": 1559007907000,
            "remark": null,
            "cartItems": [
              {
                "id": 4016742,
                "name": "测试 商品",
                "quantity": 1,
                "unitPrice": 0.01,
                "totalPrice": 0.01,
                "product": null
              }
            ]
          },
          {
            "id": 4016736,
            "no": "5cec9282d6ab53509f354921",
            "transactionId": "4200000303201905285489897004",
            "merchant": {
              "id": 4016606,
              "loginName": "周洋",
              "deviceNo": null,
              "printNo": "4016606,888",
              "approved": true,
              "openId": "oyA2pjmzFlQLvgbP1lxTESLl6K0A",
              "transferOpenId": "oV3Nlt2Q6eJxbn0HaMi2DvzvAx2k",
              "name": "吕瑞江私房牛肉面",
              "realName": "周洋",
              "phone": "15258910697",
              "mail": "1074382158@qq.com",
              "open": true,
              "discount": 1.0,
              "takeByPhone": true,
              "takeByPhoneSuffix": true,
              "rate": 0.02,
              "autoPayment": true,
              "trash": null,
              "takeOut": null,
              "user": null
            },
            "customer": {
              "id": 4016343,
              "loginName": "爆米花",
              "openId": "oV3Nlt4lvutSgkYAkrdW_IEd0Icc",
              "name": "爆米花",
              "cardNo": null,
              "phone": "1773153535622",
              "mail": null,
              "createdOn": 1557234019000
            },
            "status": 3,
            "totalPrice": 0.01,
            "payTime": 1559008475000,
            "createdOn": 1559007875000,
            "remark": null,
            "cartItems": [
              {
                "id": 4016741,
                "name": "测试 商品",
                "quantity": 1,
                "unitPrice": 0.01,
                "totalPrice": 0.01,
                "product": null
              }
            ]
          },
          {
            "id": 4016730,
            "no": "5cec833dd6ab53509f354920",
            "transactionId": "4200000294201905283068027440",
            "merchant": {
              "id": 4016606,
              "loginName": "周洋",
              "deviceNo": null,
              "printNo": "4016606,888",
              "approved": true,
              "openId": "oyA2pjmzFlQLvgbP1lxTESLl6K0A",
              "transferOpenId": "oV3Nlt2Q6eJxbn0HaMi2DvzvAx2k",
              "name": "吕瑞江私房牛肉面",
              "realName": "周洋",
              "phone": "15258910697",
              "mail": "1074382158@qq.com",
              "open": true,
              "discount": 1.0,
              "takeByPhone": true,
              "takeByPhoneSuffix": true,
              "rate": 0.02,
              "autoPayment": true,
              "trash": null,
              "takeOut": null,
              "user": null
            },
            "customer": {
              "id": 4016343,
              "loginName": "爆米花",
              "openId": "oV3Nlt4lvutSgkYAkrdW_IEd0Icc",
              "name": "爆米花",
              "cardNo": null,
              "phone": "1773153535622",
              "mail": null,
              "createdOn": 1557234019000
            },
            "status": 3,
            "totalPrice": 0.01,
            "payTime": 1559004566000,
            "createdOn": 1559003966000,
            "remark": null,
            "cartItems": [
              {
                "id": 4016735,
                "name": "测试 商品",
                "quantity": 1,
                "unitPrice": 0.01,
                "totalPrice": 0.01,
                "product": null
              }
            ]
          },
          {
            "id": 4016729,
            "no": "5cec81cad6ab53509f35491f",
            "transactionId": "4200000293201905280776323483",
            "merchant": {
              "id": 4016606,
              "loginName": "周洋",
              "deviceNo": null,
              "printNo": "4016606,888",
              "approved": true,
              "openId": "oyA2pjmzFlQLvgbP1lxTESLl6K0A",
              "transferOpenId": "oV3Nlt2Q6eJxbn0HaMi2DvzvAx2k",
              "name": "吕瑞江私房牛肉面",
              "realName": "周洋",
              "phone": "15258910697",
              "mail": "1074382158@qq.com",
              "open": true,
              "discount": 1.0,
              "takeByPhone": true,
              "takeByPhoneSuffix": true,
              "rate": 0.02,
              "autoPayment": true,
              "trash": null,
              "takeOut": null,
              "user": null
            },
            "customer": {
              "id": 4016343,
              "loginName": "爆米花",
              "openId": "oV3Nlt4lvutSgkYAkrdW_IEd0Icc",
              "name": "爆米花",
              "cardNo": null,
              "phone": "1773153535622",
              "mail": null,
              "createdOn": 1557234019000
            },
            "status": 3,
            "totalPrice": 8.00,
            "payTime": 1559004195000,
            "createdOn": 1559003595000,
            "remark": null,
            "cartItems": [
              {
                "id": 4016734,
                "name": "雪菜肉丝面",
                "quantity": 1,
                "unitPrice": 8.00,
                "totalPrice": 8.00,
                "product": null
              }
            ]
          }
        ],
        "last": false,
        "totalPages": 2,
        "totalElements": 16,
        "first": true,
        "sort": null,
        "numberOfElements": 10,
        "size": 10,
        "number": 0
      });
    }

    function getBills() {
      return ok({
        "content": [
          {
            "id": 792,
            "no": "5d2159285a4c0655fc45c57a",
            "statDate": "2019-07-06",
            "openId": "oV3Nlt3STVQzJvwVX14I61Z26L7g",
            "name": "好财 八戒板面",
            "status": 1,
            "totalPrice": 10.00,
            "rate": 0.03,
            "serviceCharge": 0.30,
            "payment": 9.70,
            "agentRate": 0.005,
            "weixinEarning": 0.06,
            "agentEarning": 0.05,
            "ticketEarning": 0.19,
            "merchant": null,
            "user": null,
            "createdOn": 1562466600000,
            "updatedOn": 1562466602000,
            "version": 1
          },
          {
            "id": 790,
            "no": "5d2007a85a4c0655fc45c579",
            "statDate": "2019-07-05",
            "openId": "oV3Nlt-UU8Ol87XADYsYXVEn5LnA",
            "name": "康萌预约宝展示账户",
            "status": 1,
            "totalPrice": 1.38,
            "rate": 0.01,
            "serviceCharge": 0.01,
            "payment": 1.37,
            "agentRate": 0.0,
            "weixinEarning": 0.01,
            "agentEarning": 0.00,
            "ticketEarning": 0.01,
            "merchant": null,
            "user": null,
            "createdOn": 1562380200000,
            "updatedOn": 1562380261000,
            "version": 1
          },
          {
            "id": 791,
            "no": "5d2007a85a4c0655fc45c577",
            "statDate": "2019-07-05",
            "openId": "oV3Nlt3STVQzJvwVX14I61Z26L7g",
            "name": "好财 八戒板面",
            "status": 1,
            "totalPrice": 16.00,
            "rate": 0.03,
            "serviceCharge": 0.48,
            "payment": 15.52,
            "agentRate": 0.005,
            "weixinEarning": 0.10,
            "agentEarning": 0.08,
            "ticketEarning": 0.30,
            "merchant": null,
            "user": null,
            "createdOn": 1562380200000,
            "updatedOn": 1562380202000,
            "version": 1
          },
          {
            "id": 788,
            "no": "5d1eb6285a4c0655fc45c573",
            "statDate": "2019-07-04",
            "openId": "oV3Nlt-UU8Ol87XADYsYXVEn5LnA",
            "name": "康萌预约宝展示账户",
            "status": 0,
            "totalPrice": 0.24,
            "rate": 0.01,
            "serviceCharge": 0.00,
            "payment": 0.24,
            "agentRate": 0.0,
            "weixinEarning": 0.00,
            "agentEarning": 0.00,
            "ticketEarning": 0.00,
            "merchant": null,
            "user": null,
            "createdOn": 1562293800000,
            "updatedOn": 1562293800000,
            "version": 0
          },
          {
            "id": 789,
            "no": "5d1eb6285a4c0655fc45c575",
            "statDate": "2019-07-04",
            "openId": "oV3Nlt3STVQzJvwVX14I61Z26L7g",
            "name": "好财 八戒板面",
            "status": 1,
            "totalPrice": 17.00,
            "rate": 0.03,
            "serviceCharge": 0.51,
            "payment": 16.49,
            "agentRate": 0.005,
            "weixinEarning": 0.10,
            "agentEarning": 0.08,
            "ticketEarning": 0.32,
            "merchant": null,
            "user": null,
            "createdOn": 1562293800000,
            "updatedOn": 1562293802000,
            "version": 1
          },
          {
            "id": 786,
            "no": "5d1e08305a4c0655fc45c56f",
            "statDate": "2019-07-03",
            "openId": "oV3Nlt-UU8Ol87XADYsYXVEn5LnA",
            "name": "康萌预约宝展示账户",
            "status": 1,
            "totalPrice": 1.18,
            "rate": 0.01,
            "serviceCharge": 0.01,
            "payment": 1.17,
            "agentRate": 0.0,
            "weixinEarning": 0.01,
            "agentEarning": 0.00,
            "ticketEarning": 0.00,
            "merchant": null,
            "user": null,
            "createdOn": 1562249265000,
            "updatedOn": 1562249267000,
            "version": 1
          },
          {
            "id": 787,
            "no": "5d1e08305a4c0655fc45c570",
            "statDate": "2019-07-03",
            "openId": "oV3Nlt3STVQzJvwVX14I61Z26L7g",
            "name": "好财 八戒板面",
            "status": 1,
            "totalPrice": 22.00,
            "rate": 0.03,
            "serviceCharge": 0.66,
            "payment": 21.34,
            "agentRate": 0.005,
            "weixinEarning": 0.13,
            "agentEarning": 0.11,
            "ticketEarning": 0.42,
            "merchant": null,
            "user": null,
            "createdOn": 1562249265000,
            "updatedOn": 1562249268000,
            "version": 1
          },
          {
            "id": 783,
            "no": "5d1970285a4c0645095663d2",
            "statDate": "2019-06-30",
            "openId": "oV3Nlt-UU8Ol87XADYsYXVEn5LnA",
            "name": "康萌预约宝展示账户",
            "status": 0,
            "totalPrice": 0.04,
            "rate": 0.01,
            "serviceCharge": 0.00,
            "payment": 0.04,
            "agentRate": 0.0,
            "weixinEarning": 0.00,
            "agentEarning": 0.00,
            "ticketEarning": 0.00,
            "merchant": null,
            "user": null,
            "createdOn": 1561948200000,
            "updatedOn": 1561948200000,
            "version": 0
          },
          {
            "id": 784,
            "no": "5d1970285a4c0645095663d4",
            "statDate": "2019-06-30",
            "openId": "oV3Nlt3STVQzJvwVX14I61Z26L7g",
            "name": "好财 八戒板面",
            "status": 1,
            "totalPrice": 20.00,
            "rate": 0.03,
            "serviceCharge": 0.60,
            "payment": 19.40,
            "agentRate": 0.005,
            "weixinEarning": 0.12,
            "agentEarning": 0.10,
            "ticketEarning": 0.38,
            "merchant": null,
            "user": null,
            "createdOn": 1561948200000,
            "updatedOn": 1561948202000,
            "version": 1
          },
          {
            "id": 781,
            "no": "5d181ea85a4c0645095663cc",
            "statDate": "2019-06-29",
            "openId": "oV3Nlt-UU8Ol87XADYsYXVEn5LnA",
            "name": "康萌预约宝展示账户",
            "status": 0,
            "totalPrice": 0.04,
            "rate": 0.01,
            "serviceCharge": 0.00,
            "payment": 0.04,
            "agentRate": 0.0,
            "weixinEarning": 0.00,
            "agentEarning": 0.00,
            "ticketEarning": 0.00,
            "merchant": null,
            "user": null,
            "createdOn": 1561861800000,
            "updatedOn": 1561861800000,
            "version": 0
          }
        ],
        "last": false,
        "totalPages": 6,
        "totalElements": 57,
        "first": true,
        "sort": null,
        "numberOfElements": 10,
        "size": 10,
        "number": 0
      });
    }

    function getAgentBills() {
      return ok({
        "content": [
          {
            "id": 796,
            "no": "5d22b1b05a4c0655fc45c57e",
            "statBeginDate": "2019-07-01",
            "statEndDate": "2019-07-07",
            "openId": "oV3Nlt4LKLYV0zFR86lULgI9gJGg",
            "name": "祖广祥",
            "status": 1,
            "earning": 0.32,
            "user": null,
            "createdOn": 1562554800000,
            "updatedOn": 1562554802000,
            "version": 1
          },
          {
            "id": 742,
            "no": "5d1969205a4c0645095663d0",
            "statBeginDate": "2019-06-24",
            "statEndDate": "2019-06-30",
            "openId": "oV3Nlt4LKLYV0zFR86lULgI9gJGg",
            "name": "祖广祥",
            "status": 1,
            "earning": 0.55,
            "user": null,
            "createdOn": 1561946400000,
            "updatedOn": 1561946402000,
            "version": 1
          },
          {
            "id": 741,
            "no": "5ceb44a05a4c064509566384",
            "statBeginDate": "2019-05-20",
            "statEndDate": "2019-05-26",
            "openId": "oV3Nlt4LKLYV0zFR86lULgI9gJGg",
            "name": "祖广祥",
            "status": 0,
            "earning": 0.08,
            "user": null,
            "createdOn": 1558922400000,
            "updatedOn": 1558922400000,
            "version": 0
          }
        ],
        "last": true,
        "totalPages": 1,
        "totalElements": 3,
        "first": true,
        "sort": null,
        "numberOfElements": 3,
        "size": 10,
        "number": 0
      });
    }

    function earning() {
      return ok('4.92');
    }

    function getAgentPage() {
      return ok({
        "content": [
          {
            "id": 16,
            "name": "陈昊",
            "loginName": "chenhao",
            "password": "$2a$10$48jApaeS/yRIeMcUma5uzuH.k1mcXwIIbiZRTzCtWKQXuDSKPztRW",
            "phone": "13817475681",
            "email": "hao.chen21@gmail.com",
            "profile": 0,
            "rate": null,
            "transferOpenId": null,
            "merchants": null,
            "version": 1,
            "authorities": null
          },
          {
            "id": 31,
            "name": "康萌",
            "loginName": "kangmeng",
            "password": "$2a$10$QfzE9Lp17VSITARMcv1dJOQIhbiZuPyt8RewIBqf2pjBKogq5NZzu",
            "phone": "13817475681",
            "email": "1377008716@qq.com",
            "profile": 0,
            "rate": null,
            "transferOpenId": null,
            "merchants": null,
            "version": 0,
            "authorities": null
          },
          {
            "id": 41,
            "name": "陈昊01",
            "loginName": "chenhao01",
            "password": "$2a$10$AbrdbERG7ig1xIbk3eDNFOPoDthpWM8Lmf7nRY0rfATf8fZBjwhTS",
            "phone": "13223432345",
            "email": "chenhao01@163.com",
            "profile": 1,
            "rate": null,
            "transferOpenId": null,
            "merchants": null,
            "version": 0,
            "authorities": null
          },
          {
            "id": 141,
            "name": "小石",
            "loginName": "xiaoshi",
            "password": "$2a$10$T.cTEwsV86lqH0I4Gy.0MOHlXXjjU9la0Wxb9TT26tWixaglfTrUy",
            "phone": "17717276516",
            "email": "1249873232@qq.com",
            "profile": 1,
            "rate": 0.001,
            "transferOpenId": "oV3NltyvMBZBa9-NnJCJ1iGixfpQ",
            "merchants": null,
            "version": 0,
            "authorities": null
          },
          {
            "id": 736,
            "name": "祖广祥",
            "loginName": "zgx",
            "password": "$2a$10$48jApaeS/yRIeMcUma5uzuH.k1mcXwIIbiZRTzCtWKQXuDSKPztRW",
            "phone": "17731535622",
            "email": "17731535622@qq.com",
            "profile": 1,
            "rate": 0.005,
            "transferOpenId": "oV3Nlt4LKLYV0zFR86lULgI9gJGg",
            "merchants": null,
            "version": 1,
            "authorities": null
          }
        ],
        "last": true,
        "totalPages": 1,
        "totalElements": 5,
        "first": true,
        "sort": null,
        "numberOfElements": 5,
        "size": 10,
        "number": 0
      });
    }

    function findAgent() {
      return ok({
        "id": 16,
        "name": "陈昊",
        "loginName": "chenhao",
        "password": "$2a$10$48jApaeS/yRIeMcUma5uzuH.k1mcXwIIbiZRTzCtWKQXuDSKPztRW",
        "phone": "13817475681",
        "email": "hao.chen21@gmail.com",
        "profile": 0,
        "rate": null,
        "transferOpenId": null,
        "merchants": null,
        "version": 1,
        "authorities": null
      });
    }

    function ok(body?) {
      return of(new HttpResponse({ status: 200, body }))
    }
  }
}

export const fakeBackendProvider = {
  // use fake backend in place of Http service for backend-less development
  provide: HTTP_INTERCEPTORS,
  useClass: FakeBackendInterceptor,
  multi: true
};
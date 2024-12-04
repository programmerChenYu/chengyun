import { createRoot } from 'react-dom/client'
import './index.css'
import {RouterProvider} from "react-router-dom";
import {router} from "./router";
import {Provider} from "react-redux";
import store from "./store";
import '../public/iconfont/iconfont-svg.js';
// import NotFound from "./pages/Errors/NotFound.tsx";
// import Test from "./pages/Errors/Test.tsx";

createRoot(document.getElementById('root')!).render(
    <Provider store={store}>
        <RouterProvider router={router} />
        {/*<Test />*/}
    </Provider>
)
